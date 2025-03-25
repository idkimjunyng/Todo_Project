package com.example.todo_project.service;

import com.example.todo_project.dto.TodoRequestDto;
import com.example.todo_project.dto.TodoResponseDto;
import com.example.todo_project.entity.Todo;
import com.example.todo_project.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }



    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {

        Todo todo = new Todo(requestDto.getMember_name(), requestDto.getTodo_content(), requestDto.getTodo_password());

        return todoRepository.createTodo(todo);
    }

    @Override
    public List<TodoResponseDto> findAllTodos() {

        List<TodoResponseDto> allTodos = todoRepository.findAllTodos();

        return allTodos;
    }

    @Override
    public List<TodoResponseDto> findSearchTodos(String start_date, String end_date) {
        List<TodoResponseDto> searchTodos = todoRepository.findSearchTodos(start_date, end_date);

        return searchTodos;
    }

    @Override
    public TodoResponseDto findTodoById(Long id) {

        Todo todo = todoRepository.findTodoByIdOrElseThrow(id);

        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, String member_name, String todo_content, String todo_password) {


        if (member_name == null || todo_content == null || todo_password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        if(todoRepository.findTodoById(id).get().getTodo_password().equals(todo_password)) {
            int updatedRow = todoRepository.updateTodo(id, member_name, todo_content, todo_password);

            if (updatedRow == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요.");
        }


        return new TodoResponseDto(todoRepository.findTodoById(id).get());
    }



    @Override
    public void deleteTodo(Long id, String password) {

        if(todoRepository.findTodoById(id).get().getTodo_password().equals(password)) {
            int deletedRow = todoRepository.deleteTodo(id, password);

            if (deletedRow == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요.");
        }

    }
}
