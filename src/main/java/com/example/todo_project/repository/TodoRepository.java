package com.example.todo_project.repository;

import com.example.todo_project.dto.TodoResponseDto;
import com.example.todo_project.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    TodoResponseDto createTodo(Todo todo);

    List<TodoResponseDto> findAllTodos();

    List<TodoResponseDto> findSearchTodos(String start_date, String end_date);

    Optional<Todo> findTodoById(Long id);

    Todo findTodoByIdOrElseThrow(Long todo_id);

    int updateTodo(Long id, String member_name, String todo_content, String todo_password);

    int deleteTodo(Long id, String password);
}
