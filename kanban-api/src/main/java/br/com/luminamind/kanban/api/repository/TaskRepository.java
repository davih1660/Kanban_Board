package br.com.luminamind.kanban.api.repository;

import br.com.luminamind.kanban.api.model.Status;
import br.com.luminamind.kanban.api.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();

    private final AtomicLong idCounter = new AtomicLong();

    public Task save(Task task) {
        if (task.getId() == null) {

            long newId = idCounter.incrementAndGet();
            task.setId(newId);
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> findAll() {
        return List.copyOf(tasks.values());
    }

    // Novo método para encontrar por status
    public List<Task> findByStatus(Status status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Task findById(Long id) {
        return tasks.get(id);
    }

    public void deleteById(Long id) {
        tasks.remove(id);
    }
}