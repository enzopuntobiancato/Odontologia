package com.utn.tesis.service.initialization;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 29/05/15
 * Time: 00:12
 */
public class InitVariables {
    private static InitVariables instance;

    private boolean initializationRunned;

    private InitVariables(){
        this.initializationRunned = true;
    }

    public static synchronized InitVariables getInstance(){
        if (instance == null) {
            instance = new InitVariables();
        }
        return instance;
    }

    boolean isInitializationRunned() {
        return initializationRunned;
    }

    void setInitializationRunned(boolean initializationRunned) {
        this.initializationRunned = initializationRunned;
    }
}
