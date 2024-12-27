package com.github.kadehar.inno.data.api;

import com.github.kadehar.inno.data.model.Task;

import java.io.IOException;
import java.util.List;

public interface TasksApi {
    Task createNew(String taskName) throws IOException;
    List<Task> allTasks() throws IOException;
    void delete(int id) throws IOException;
    Task update(Task task) throws IOException;
    Task updateStatus(Task task) throws IOException;
}
