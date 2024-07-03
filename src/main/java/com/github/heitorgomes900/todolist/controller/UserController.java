package com.github.heitorgomes900.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.heitorgomes900.todolist.model.User;
import com.github.heitorgomes900.todolist.repository.IUserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository repository;

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return null;
    }
    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        var anotherUser = this.repository.findByUsername(user.getUsername());

        if (anotherUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));

        var userCreated = this.repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
