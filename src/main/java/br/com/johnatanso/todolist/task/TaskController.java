package br.com.johnatanso.todolist.task;

import br.com.johnatanso.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        var userId = request.getAttribute("userId");

        taskModel.setUserId((UUID) userId);

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de inicio deve ser maior que a data atual");
        }

        if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de término deve ser maior que a data de início");
        }

        var newTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping("/")
    public ResponseEntity listTasks(HttpServletRequest request) {

        var userId = request.getAttribute("userId");

        List<TaskModel> tasks = this.taskRepository.findByUserId((UUID) userId);

        return ResponseEntity.status(200).body(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity updateTask(@RequestBody TaskModel taskModel, @PathVariable UUID taskId, HttpServletRequest request) {

        var task = this.taskRepository.findById(taskId).orElse(null);

        Utils.copyNonNullProperties(taskModel, task);

        this.taskRepository.save(task);

        return ResponseEntity.status(201).body("Tarefa atualizada com sucesso");
    }
}
