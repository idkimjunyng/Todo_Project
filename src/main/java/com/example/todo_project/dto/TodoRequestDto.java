package com.example.todo_project.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String member_name;
    private String todo_content;
    private String todo_password;
}
