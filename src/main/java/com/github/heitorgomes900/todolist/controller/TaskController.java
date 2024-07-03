package com.github.heitorgomes900.todolist.controller;

import com.github.heitorgomes900.todolist.model.Task;
import com.github.heitorgomes900.todolist.repository.ITaskRepository;
import com.github.heitorgomes900.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Response;
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
    private ITaskRepository repository;

    @GetMapping("/")
    public List<Task> list(HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        System.out.println(idUser);
        var tasks = this.repository.findByIdUser(idUser);
        tasks.stream().forEach(System.out::println);
        return tasks;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request){
        task.setIdUser((UUID) request.getAttribute("idUser"));
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início e término devem ser maior do que a data atual");
        }
        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser menor do que a data do término");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Task taskUpdate, HttpServletRequest request, @PathVariable UUID id) {
        var task = repository.findById(id).orElse(null);

        if(task == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }
        var idUser = request.getAttribute("idUser");
        if(!task.getIdUser().equals(idUser)) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário sem permissão para alterar essa tarefa");
        }
        Utils.copyNonNullProperties(taskUpdate, task);
        var tarkUpdated = repository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(tarkUpdated);
    }
}
