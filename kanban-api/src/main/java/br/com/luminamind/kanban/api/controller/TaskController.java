package br.com.luminamind.kanban.api.controller;

import br.com.luminamind.kanban.api.model.Status;
import br.com.luminamind.kanban.api.model.Task;
import br.com.luminamind.kanban.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> listTasks(@RequestParam(required = false) Status status) {
        if (status == null) {
            return taskRepository.findAll();
        } else {
            return taskRepository.findByStatus(status);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        task.setStatus(Status.TO_DO);
        return taskRepository.save(task);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestParam Status newStatus) {
        // @PathVariable: Pega o "id" da URL
        // @RequestParam: Pega o "newStatus" dos parâmetros da URL
        Task existingTask = taskRepository.findById(id);
        if (existingTask == null) {
            return ResponseEntity.notFound().build();
        }
        existingTask.setStatus(newStatus);
        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}