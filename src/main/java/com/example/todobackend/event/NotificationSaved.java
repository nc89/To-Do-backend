package com.example.todobackend.event;

import com.example.todobackend.entity.Notification;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NotificationSaved implements Event{
    private Notification notification;
}
