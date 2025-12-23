package com.provframework.capture.cypher;

import com.provframework.capture.StreamUtils;
import com.provframework.capture.prov.Activity;
import com.provframework.capture.prov.Agent;
import com.provframework.capture.prov.Bundle;
import com.provframework.capture.prov.Entity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;
import org.neo4j.cypherdsl.core.StatementBuilder.OngoingMerge;
import org.neo4j.cypherdsl.core.PatternElement;
import org.neo4j.cypherdsl.core.Relationship;
import org.springframework.stereotype.Component;

@Component
public class CypherLang {

    public Statement getInsertStatement(Bundle bundle) {
        List<PatternElement> elements = new ArrayList<>();

        StreamUtils.getNonNullStream(bundle.getEntities()).forEach(entity -> insertEntity(elements, entity));
        StreamUtils.getNonNullStream(bundle.getActivities()).forEach(activity -> insertActivity(elements, activity));
        StreamUtils.getNonNullStream(bundle.getAgents()).forEach(agent -> insertAgent(elements, agent));

        OngoingMerge statement = Cypher.merge(elements.get(0));

        elements.stream().skip(1).forEach(element -> {
            statement.merge(element);
        });

        return statement.build();
    }

    private void insertEntity(List<PatternElement> elements, Entity entity) {
        Node entityNode = createNode(entity.getId(), PROV.ENTITY);

        addRelatedNodes(elements, entityNode, PROV.WAS_DERIVED_FROM, PROV.ENTITY, entity.getWasDerivedFrom());
        addRelatedNodes(elements, entityNode, PROV.WAS_ATTRIBUTED_TO, PROV.AGENT, entity.getWasAttributedTo());
        addRelatedNodes(elements, entityNode, PROV.WAS_GENERATED_BY, PROV.ACTIVITY, entity.getWasGeneratedBy());
    }

    private void insertActivity(List<PatternElement> elements, Activity activity) {
        Node activityNode = createNode(activity.getId(), PROV.ACTIVITY);

        // Need to override properties for activity
        activityNode.withProperties(
            RDFS.LABEL.getLocalName(), Cypher.literalOf(activity.getId()),
            PROV.STARTED_AT_TIME.getLocalName(), Cypher.literalOf(activity.getStartedAtTime().toString()),
            PROV.ENDED_AT_TIME.getLocalName(), Cypher.literalOf(activity.getEndedAtTime().toString()),
            PROV.AT_LOCATION.getLocalName(), Cypher.literalOf(activity.getAtLocation())
        );

        addRelatedNodes(elements, activityNode, PROV.WAS_ASSOCIATED_WITH, PROV.AGENT, activity.getWasAssociatedWith());
        addRelatedNodes(elements, activityNode, PROV.USED, PROV.ENTITY, activity.getUsed());
        addRelatedNodes(elements, activityNode, PROV.WAS_INFORMED_BY, PROV.ACTIVITY, activity.getWasInformedBy());
    }

    private void insertAgent(List<PatternElement> elements, Agent agent) {
        Node agentNode = createNode(agent.getId(), PROV.AGENT);

        addRelatedNodes(elements, agentNode, PROV.ACTED_ON_BEHALF_OF, PROV.AGENT, agent.getActedOnBehalfOf());
    }

    private void addRelatedNodes(List<PatternElement> elements, Node primaryNode, IRI edgeLabel, IRI nodeType, 
        List<String> relatedNodeLabels) {
        StreamUtils.getNonNullStream(relatedNodeLabels)
        .forEach(relatedNode -> {
            Node secondaryNode = createNode(relatedNode, nodeType);
            Relationship relationship = primaryNode.relationshipTo(secondaryNode, edgeLabel.getLocalName());
            elements.add(relationship);
        });
    }

    private Node createNode(String id, IRI type) {
        return Cypher.node(type.getLocalName())
            .named(id)
            .withProperties(
                RDFS.LABEL.getLocalName(), Cypher.literalOf(id)
            );
    }
}
