package com.divyanshu.todolist.controller;

import com.divyanshu.todolist.dto.UpdateStatusRequest;
import com.divyanshu.todolist.entity.Todo;
import com.divyanshu.todolist.service.TodoJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoJpaService todoJpaService;

    @Autowired
    public TodoController(TodoJpaService todoJpaService) {
        this.todoJpaService = todoJpaService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoJpaService.getAllTodos();
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
        Todo createdTodo = todoJpaService.createTodo(newTodo);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable int id) {
        try {
            Todo todo = todoJpaService.getTodoById(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable int id, @RequestBody UpdateStatusRequest request) {
        try {
            Todo updatedTodo = todoJpaService.updateTodoStatus(id, request.getStatus());
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable int id) {
        try {
            todoJpaService.deleteTodoById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
