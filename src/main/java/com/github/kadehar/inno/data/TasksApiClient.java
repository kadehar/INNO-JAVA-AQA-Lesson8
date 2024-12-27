package com.github.kadehar.inno.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kadehar.inno.data.api.TasksApi;
import com.github.kadehar.inno.data.model.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class TasksApiClient implements TasksApi {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final HttpClient httpClient;
    private final String url;

    public TasksApiClient(String url) {
        this(HttpClientBuilder.create().build(), url);
    }

    public TasksApiClient(HttpClient httpClient, String url) {
        this.httpClient = httpClient;
        this.url = url;
    }

    @Override
    public Task createNew(String taskName) throws IOException {
        String jsonBody = """ 
                { "title": "%s" }
                """.formatted(taskName);
        HttpPost createTaskRequest = new HttpPost(url);
        StringEntity requestDto = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        createTaskRequest.setEntity(requestDto);
        String responseBody = getResponse(createTaskRequest);
        return MAPPER.readValue(responseBody, Task.class);
    }

    @Override
    public List<Task> allTasks() throws IOException {
        HttpGet fetchAll = new HttpGet(url);
        String responseBody = getResponse(fetchAll);
        return MAPPER.readerForListOf(Task.class).readValue(responseBody);
    }

    @Override
    public void delete(int id) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(urlWithId(id));
        httpClient.execute(deleteRequest);
    }

    @Override
    public Task update(Task task) throws IOException {
        HttpPatch updateRequest = new HttpPatch(urlWithId(task.id()));
        String jsonBody = MAPPER.writeValueAsString(task);
        StringEntity requestDto = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        updateRequest.setEntity(requestDto);
        String responseBody = getResponse(updateRequest);
        return MAPPER.readValue(responseBody, Task.class);
    }

    @Override
    public Task updateStatus(Task task) throws IOException {
        HttpPatch updateRequest = new HttpPatch(urlWithId(task.id()));
        String jsonBody = "{ \"completed\": true }";
        StringEntity requestDto = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        updateRequest.setEntity(requestDto);
        String responseBody = getResponse(updateRequest);
        return MAPPER.readValue(responseBody, Task.class);
    }

    private String getResponse(HttpUriRequest request) throws IOException {
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    private String urlWithId(int id) {
        return url + "/" + id;
    }
}
