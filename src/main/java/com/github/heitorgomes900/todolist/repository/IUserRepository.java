package com.github.heitorgomes900.todolist.repository;

import com.github.heitorgomes900.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

}
