package com.github.kadehar.inno.tests;

import com.github.kadehar.inno.config.ApiConfig;
import com.github.kadehar.inno.data.TasksApiClient;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static TasksApiClient tasksApiClient;

    @BeforeAll
    public static void setUp() {
        String url = ApiConfig.INSTANCE.getConfig().baseUrl();
        tasksApiClient = new TasksApiClient(url);
    }
}
