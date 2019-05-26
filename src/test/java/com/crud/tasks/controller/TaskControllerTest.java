package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchTasks() throws Exception{
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Task Title", "Task Content"));
        taskDtoList.add(new TaskDto());
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task Title")))
                .andExpect(jsonPath("$[0].content", is("Task Content")));
    }
    @Test
    public void shouldFetchTask() throws Exception{
        TaskDto taskDto = new TaskDto(1L, "Task Title", "Task Content");
        Task task = new Task(1L, "Task Title", "Task Content");
        TaskDto emptyTaskDto = new TaskDto();
        Task emptyTask = new Task();
        when(service.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.getTask(2L)).thenReturn(Optional.of(emptyTask));
        when(taskMapper.mapToTaskDto(emptyTask)).thenReturn(emptyTaskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task Title")))
                .andExpect(jsonPath("$.content", is("Task Content")));
        mockMvc.perform(get("/v1/task/getTask")
                .param("taskId", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(anyOf(nullValue())))
                .andExpect(jsonPath("$.title").value(anyOf(nullValue())))
                .andExpect(jsonPath("$.content").value(anyOf(nullValue())));
    }
    @Test
    public void shouldDeleteTask() throws Exception{
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask")
                .param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldUpdateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task Title", "Task Content");
        Task task = new Task(1l, "Task Title", "Task Content");
        TaskDto updatedTaskDto = new TaskDto(1L, "Title Updated", "Content Updated");
        Task updatedTask = new Task(1L, "Title Updated", "Content Updated");
        TaskDto taskDtoToUpdate = new TaskDto(1L, "Title Updated", "Content Updated");
        when(taskMapper.mapToTask(eq(updatedTaskDto))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(updatedTask);
        when(taskMapper.mapToTaskDto(updatedTask)).thenReturn(updatedTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoToUpdate);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title Updated")))
                .andExpect(jsonPath("$.content", is("Content Updated")));
    }
    @Test
    public void shouldCreateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task Title", "Task Content");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}