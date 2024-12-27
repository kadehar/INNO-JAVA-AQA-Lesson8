package com.github.kadehar.inno.config;

import org.aeonbits.owner.ConfigFactory;

public enum ApiConfig {
    INSTANCE;

    private static final TasksApiConfig config = ConfigFactory.create(
            TasksApiConfig.class,
            System.getProperties()
    );

    public TasksApiConfig getConfig() {
        return config;
    }
}
