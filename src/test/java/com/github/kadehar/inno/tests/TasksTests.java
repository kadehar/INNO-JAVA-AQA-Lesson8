package com.github.kadehar.inno.tests;

import com.github.kadehar.inno.data.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TasksTests extends TestBase {

    @Test
    @DisplayName("Могу создать новую задачу в To Do листе")
    void canCreateNewTask() throws IOException {
        String taskName = "Моя первая задача в To Do листе для теста создания задач";
        Boolean expectedStatus = false;
        Task actualTask = tasksApiClient.createNew(taskName);
        assertThat(actualTask.title()).isEqualTo(taskName);
        assertThat(actualTask.completed()).isEqualTo(expectedStatus);
    }

    @Test
    @DisplayName("Могу получить список всех задач в To Do листе")
    void canGetAllTasks() throws IOException {
        String taskName = "Моя очередная задача в To Do листе для теста списка задач";
        Task task = tasksApiClient.createNew(taskName);
        List<Task> tasks = tasksApiClient.allTasks();
        assertThat(tasks).contains(task);
        assertThat(tasks).isNotEmpty();
    }

    @Test
    @DisplayName("Могу удалить задачу из To Do листа")
    void canDeleteTask() throws IOException {
        String taskName = "Моя очередная задача в To Do листе для теста удаления задач";
        Task task = tasksApiClient.createNew(taskName);
        tasksApiClient.delete(task.id());
        List<Task> tasks = tasksApiClient.allTasks();
        assertThat(tasks).doesNotContain(task);
    }

    @Test
    @DisplayName("Могу изменить название задачи в To Do листе")
    void canChangeTaskName() throws IOException {
        String taskName = "Моя первая задача в To Do листе для теста переименования задач";
        Task task = tasksApiClient.createNew(taskName);
        String updatedName = "Моя задача в To Do листе для теста переименования задач";
        Task updatedTitleTask = new Task(task.id(), updatedName, task.completed());
        Task actualTask = tasksApiClient.update(updatedTitleTask);
        assertThat(actualTask.title()).isEqualTo(updatedTitleTask.title());
        assertThat(actualTask.id()).isEqualTo(updatedTitleTask.id());
        assertThat(actualTask.completed()).isEqualTo(updatedTitleTask.completed());
    }

    @Test
    @DisplayName("Могу изменить статус задачи в To Do листе")
    void canChangeTaskStatus() throws IOException {
        String taskName = "Моя задача в To Do листе для теста смены статуса задач";
        Task task = tasksApiClient.createNew(taskName);
        Task updatedStatusTask = new Task(task.id(), task.title(), true);
        Task actualTask = tasksApiClient.updateStatus(updatedStatusTask);
        assertThat(actualTask.title()).isEqualTo(updatedStatusTask.title());
        assertThat(actualTask.id()).isEqualTo(updatedStatusTask.id());
        assertThat(actualTask.completed()).isEqualTo(updatedStatusTask.completed());
    }
}
