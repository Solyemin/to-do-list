package com.divyanshu.todolist.service;

import com.divyanshu.todolist.entity.Todo;
import com.divyanshu.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoJpaService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoJpaService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo newTodo) {
        return todoRepository.save(newTodo);
    }

    public Todo getTodoById(int id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        return optionalTodo.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Todo not found with id: " + id));
    }

    public Todo updateTodoStatus(int id, String status) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Todo not found with id: " + id));

        todo.setStatus(status);
        return todoRepository.save(todo);
    }

    public void deleteTodoById(int id) {
        if (!todoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Todo not found with id: " + id);
        }

        todoRepository.deleteById(id);
    }
}
