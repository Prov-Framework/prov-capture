package com.provframework.capture.cypher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.provframework.capture.prov.Bundle;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Service
public class CypherDriver {
    @SuppressWarnings("unused")
    private final String uri;
    @SuppressWarnings("unused")
    private final String username;
    @SuppressWarnings("unused")
    private final String password;
    private final Driver driver;

    public CypherDriver(@Value("${bolt.uri}") String uri,
                @Value("${bolt.username}") String username,
                @Value("${bolt.password}") String password) {
        this.uri = uri;
        this.username = username;
        this.password = password;
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    public void insertBundle(Bundle bundle) {
        String statement = CypherLang.getInsertStatement(bundle);
		driver.executableQuery(statement).execute();
    }
}
