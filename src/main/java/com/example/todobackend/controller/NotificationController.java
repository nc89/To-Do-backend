package com.example.todobackend.controller;

import com.example.todobackend.event.Event;
import com.example.todobackend.entity.Notification;
import com.example.todobackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public Flux<Notification> getAll(){
        return notificationService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Notification> create(@RequestBody Notification notification){
        return notificationService.save(notification);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Notification> update(@RequestBody Notification notification){
        return notificationService.updateSeverity(notification, notification.getSeverity());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable final String id){
        return notificationService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/events")
    public Flux<ServerSentEvent<Event>> getEventStream(){
        return notificationService.listenToEvents()
                .map(event -> ServerSentEvent.<Event>builder()
                        .retry(Duration.ofSeconds(5))
                        .event(event.getClass().getSimpleName())
                        .data(event).build());
    }
}
