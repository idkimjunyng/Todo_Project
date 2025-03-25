package com.example.todo_project.dto;

import com.example.todo_project.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long todo_id;
    private String member_name;
    private String todo_content;
    private String todo_password;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    public TodoResponseDto(Todo todo) {
        this.todo_id = todo.getTodo_id();
        this.member_name = todo.getMember_name();
        this.todo_content = todo.getTodo_content();
        this.todo_password = todo.getTodo_password();
        this.create_at = todo.getCreate_at();
        this.update_at = todo.getUpdate_at();
    }

}
