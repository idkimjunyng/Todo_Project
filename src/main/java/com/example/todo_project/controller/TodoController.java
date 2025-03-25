package com.example.todo_project.controller;


import com.example.todo_project.dto.TodoRequestDto;
import com.example.todo_project.dto.TodoResponseDto;
import com.example.todo_project.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponseDto> findAllTodos() {
        return todoService.findAllTodos();
    }

    @GetMapping("/search/{start_date}/{end_date}")
    public List<TodoResponseDto> findSearchTodos(@PathVariable String start_date, @PathVariable String end_date) {
        return todoService.findSearchTodos(start_date, end_date);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {

        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {

        return new ResponseEntity<>(todoService.createTodo(requestDto), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto requestDto
    ) {

        return new ResponseEntity<>(todoService.updateTodo(id, requestDto.getMember_name(), requestDto.getTodo_content(), requestDto.getTodo_password()), HttpStatus.OK);
    }


    @DeleteMapping("/{id}/{password}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @PathVariable String password) {

        todoService.deleteTodo(id, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
