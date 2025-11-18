package com.provframework.capture.database.implementations;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.provframework.capture.database.DatabaseInterface;

@Component
public class SparqlEndpoint implements DatabaseInterface {
    @Value("${database.sparql.endpoint.update}")
    private String sparqlEndpointUpdate;

    @Override
    public void execute(String statement) {
        UpdateRequest updateRequest = UpdateFactory.create(statement);
        UpdateExecutionFactory.createRemote(updateRequest, sparqlEndpointUpdate).execute();
    }
    
}
