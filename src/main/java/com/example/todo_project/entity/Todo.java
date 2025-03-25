package com.example.todo_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {

    private Long todo_id;
    private String member_name;
    private String todo_content;
    private String todo_password;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    public Todo(String member_name, String todo_content, String todo_password) {

        this.member_name = member_name;
        this.todo_content = todo_content;
        this.todo_password = todo_password;

    }

}
