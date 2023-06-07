package com.example.todobackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public abstract class EntityId {

    @Id @Getter @Setter
    protected Long id;


}
