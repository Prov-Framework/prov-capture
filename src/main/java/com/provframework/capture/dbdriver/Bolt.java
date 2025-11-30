package com.provframework.capture.dbdriver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Service
public class Bolt {

    @Value("${bolt.uri}")
    private String uri;
    @Value("${bolt.username}")
    private String username;
    @Value("${bolt.password}")
    private String password;

    private Driver driver;

    public Bolt() {
        try {
            this.driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Driver getDriver() {
        return driver;
    }
}
