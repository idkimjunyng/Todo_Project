package com.example.todo_project.service;

import com.example.todo_project.dto.TodoRequestDto;
import com.example.todo_project.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    TodoResponseDto createTodo(TodoRequestDto requestDto);

    List<TodoResponseDto> findAllTodos();

    List<TodoResponseDto> findSearchTodos(String start_date, String end_date);

    TodoResponseDto findTodoById(Long id);

    TodoResponseDto updateTodo(Long id, String member_name, String todo_content, String todo_password);

    void deleteTodo(Long id, String password);

}
