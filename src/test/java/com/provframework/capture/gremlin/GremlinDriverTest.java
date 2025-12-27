package com.provframework.capture.gremlin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.util.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.provframework.capture.prov.Bundle;

class GremlinDriverTest {

    @Test
    void insertBundleCommitsAndClosesTx_evenWhenLaterCallThrows() {
        // mock GremlinLang
        GremlinLang gremlinLang = mock(GremlinLang.class);

        // mock traversal and transaction
        GraphTraversalSource gMock = mock(GraphTraversalSource.class);
        Transaction txMock = mock(Transaction.class);
        when(gMock.tx()).thenReturn(txMock);

        // gremlinLang returns gMock on first call, then throws on second call
        try {
            when(gremlinLang.getInsertStatement(Mockito.any(), Mockito.any())).thenReturn(gMock).thenThrow(new RuntimeException("boom"));
        } catch (Exception _) {
            // no-op; Mockito stubbing doesn't actually throw here
        }

        // Prevent constructor from contacting real Cluster by making Cluster.build() throw
        try (MockedStatic<Cluster> clusterStatic = Mockito.mockStatic(Cluster.class)) {
            clusterStatic.when(Cluster::build).thenThrow(new RuntimeException("no cluster"));

            GremlinDriver driver = new GremlinDriver("localhost", 8182, gremlinLang);

            Bundle bundle = new Bundle();

            // first call: should commit then close
            driver.insertBundle(bundle);

            // second call: gremlinLang throws, catch path should run and finally should close previous tx again
            driver.insertBundle(bundle);

            // verify commit called once, close called twice
            verify(txMock, times(1)).commit();
            verify(txMock, times(2)).close();
        }
    }

    @Test
    void constructorInitializesTraversal_and_insertBundleCommits() {
        GremlinLang gremlinLang = mock(GremlinLang.class);

        // Mocks for Cluster builder chain
        Cluster.Builder builderMock = mock(Cluster.Builder.class);
        Cluster clusterMock = mock(Cluster.class);

        // Mocks for traversal
        @SuppressWarnings("unchecked")
        AnonymousTraversalSource<GraphTraversalSource> anonTraversalMock = 
            (AnonymousTraversalSource<GraphTraversalSource>) mock(AnonymousTraversalSource.class);
        GraphTraversalSource initialTraversal = mock(GraphTraversalSource.class);
        GraphTraversalSource returnedTraversal = mock(GraphTraversalSource.class);
        org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection remoteMock = 
            mock(org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection.class);

        Transaction txMock = mock(Transaction.class);

        when(builderMock.addContactPoint("localhost")).thenReturn(builderMock);
        when(builderMock.port(8182)).thenReturn(builderMock);
        when(builderMock.serializer(Mockito.any(MessageSerializer.class))).thenReturn(builderMock);
        when(builderMock.create()).thenReturn(clusterMock);

        when(anonTraversalMock.withRemote(remoteMock)).thenReturn(initialTraversal);
        when(initialTraversal.tx()).thenReturn(txMock);
        when(returnedTraversal.tx()).thenReturn(txMock);

        try (MockedStatic<Cluster> clusterStatic = Mockito.mockStatic(Cluster.class);
             MockedStatic<org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection> drcStatic = Mockito.mockStatic(org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection.class);
             @SuppressWarnings("rawtypes")
             MockedStatic<AnonymousTraversalSource> atsStatic = Mockito.mockStatic(org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.class)) {

            clusterStatic.when(Cluster::build).thenReturn(builderMock);
            drcStatic.when(() -> org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection.using(clusterMock)).thenReturn(remoteMock);
            atsStatic.when(AnonymousTraversalSource::traversal).thenReturn(anonTraversalMock);

            GremlinDriver driver = new GremlinDriver("localhost", 8182, gremlinLang);

            Bundle bundle = new Bundle();
            when(gremlinLang.getInsertStatement(bundle, initialTraversal)).thenReturn(returnedTraversal);

            driver.insertBundle(bundle);

            verify(txMock).commit();
            verify(txMock).close();
        }
    }
}

