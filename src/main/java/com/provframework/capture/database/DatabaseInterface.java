package com.provframework.capture.database;

public interface DatabaseInterface {
    public void connect();
    public void execute(String statement);
}
