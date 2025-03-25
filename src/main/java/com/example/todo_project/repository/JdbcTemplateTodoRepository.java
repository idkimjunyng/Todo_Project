package com.example.todo_project.repository;

import com.example.todo_project.dto.TodoResponseDto;
import com.example.todo_project.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public TodoResponseDto createTodo(Todo todo) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("todo_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_name", todo.getMember_name());
        parameters.put("todo_content", todo.getTodo_content());
        parameters.put("todo_password", todo.getTodo_password());
        parameters.put("create_at", LocalDateTime.now());
        parameters.put("update_at", LocalDateTime.now());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new TodoResponseDto(key.longValue(), todo.getMember_name(), todo.getTodo_content(), todo.getTodo_password(), todo.getCreate_at(), todo.getUpdate_at());
    }

    @Override
    public List<TodoResponseDto> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todo", todoRowMapper());
    }

    @Override
    public List<TodoResponseDto> findSearchTodos(String start_date, String end_date) {
        return jdbcTemplate.query("SELECT * FROM todo WHERE update_at >= ? AND update_at <= ? ORDER BY update_at DESC", todoRowMapper(), start_date, end_date);
    }


    private RowMapper<TodoResponseDto> todoRowMapper() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("todo_id"),
                        rs.getString("member_name"),
                        rs.getString("todo_content"),
                        rs.getString("todo_password"),
                        rs.getTimestamp("create_at").toLocalDateTime(),
                        rs.getTimestamp("update_at").toLocalDateTime()
                );
            };
        };
    }

    @Override
    public Optional<Todo> findTodoById(Long id) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ?", todoRowMapperV2(), id);

        return result.stream().findAny();
    }

    private RowMapper<Todo> todoRowMapperV2() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("todo_id"),
                        rs.getString("member_name"),
                        rs.getString("todo_content"),
                        rs.getString("todo_password"),
                        rs.getTimestamp("create_at").toLocalDateTime(),
                        rs.getTimestamp("update_at").toLocalDateTime()
                );
            }

        };
    }


    @Override
    public int updateTodo(Long id, String member_name, String todo_content, String todo_password) {
        return jdbcTemplate.update("UPDATE todo SET member_name = ?, todo_content = ?, update_at = NOW() WHERE todo_id = ?", member_name, todo_content, id);
    }

    @Override
    public int deleteTodo(Long id, String password) {
        return jdbcTemplate.update("DELETE FROM todo WHERE todo_id = ?", id);
    }

    @Override
    public Todo findTodoByIdOrElseThrow(Long todo_id) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ?", todoRowMapperV2(), todo_id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + todo_id));
    }

}
