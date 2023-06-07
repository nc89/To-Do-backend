package com.example.todobackend.utils;

import com.example.todobackend.entity.EntityId;

import java.util.Comparator;
import java.util.List;

public class Operations {

    public static String trimBrackets(String message) {
        return message.replaceAll("[\\[\\]]", "");
    }

    public static Long autoIncrement(List<? extends EntityId> list) {
        if(list.isEmpty())
            return 1L;
        return list.stream().max(Comparator.comparing(EntityId::getId)).get().getId() + 1L;
    }
}
