package com.github.heitorgomes900.todolist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private UUID idUser;
    
    @Column(length = 50)
    private String title;
    
    @Column
    private String description;
    
    @Column
    private LocalDateTime startAt;
    
    @Column
    private LocalDateTime endAt;
    
    @Column
    private String priority;
    
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O campo de título deve possuir até 50 caracteres");
        }
        this.title = title;
    }

}
