package com.example.todobackend.repository;

import com.example.todobackend.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {

}
