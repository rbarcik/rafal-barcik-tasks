package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {
    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Title taskDto", "Content taskDto");
        TaskDto emptyTaskDto = new TaskDto();
        //When
        Task task = taskMapper.mapToTask(taskDto);
        Task emptyTask = taskMapper.mapToTask(emptyTaskDto);
        //Then
        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getContent(), task.getContent());
        assertNull(emptyTask.getId());
        assertNull(emptyTask.getTitle());
        assertNull(emptyTask.getContent());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "Title task", "Content task");
        Task emptyTask = new Task();
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        TaskDto emptyTaskDto = taskMapper.mapToTaskDto(emptyTask);
        //Then
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getContent(), taskDto.getContent());
        assertEquals(task.getContent(), taskDto.getContent());
        assertNull(emptyTaskDto.getId());
        assertNull(emptyTaskDto.getTitle());
        assertNull(emptyTaskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        Task task = new Task(1L, "Title task", "Content task");
        Task emptyTask = new Task();
        List<Task> taskList = new ArrayList<>();
        List<Task> emptyTaskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(emptyTask);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        List<TaskDto> emptyTaskDtoList = taskMapper.mapToTaskDtoList(emptyTaskList);
        //Then
        assertEquals(taskList.size(), taskDtoList.size());
        for (int i = 0; i < taskList.size(); i++) {
            assertEquals(taskList.get(i).getId(), taskDtoList.get(i).getId());
            assertEquals(taskList.get(i).getTitle(), taskDtoList.get(i).getTitle());
            assertEquals(taskList.get(i).getContent(), taskDtoList.get(i).getContent());
        }
        assertNotNull(emptyTaskDtoList);
        assertEquals(0, emptyTaskDtoList.size());
    }
}