package com.example.appsmart.controller;

import com.example.appsmart.service.answer.ServiceAnswer;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.UUID;

public class ControllerTools {
    public static boolean isNotValidUUID(String uuid) {
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(uuid);
        } catch (IllegalArgumentException exception) {
            return true;
        }
        return false;
    }

    @NotNull
    public static <T extends Serializable> ResponseEntity<Void> buildNoContentResponse(ServiceAnswer<T> serviceAnswer) {
        if (serviceAnswer.isSuccess()) {
            return ResponseEntity.noContent().build();
        }
        return buildErrorResponse(serviceAnswer.getStatus());
    }

    @NotNull
    public static <T extends Serializable> ResponseEntity<T> buildEntityResponse(ServiceAnswer<T> serviceAnswer) {
        if (serviceAnswer.isSuccess()) {
            return ResponseEntity.ok(serviceAnswer.getObj());
        }
        return buildErrorResponse(serviceAnswer.getStatus());
    }

    @NotNull
    public static <T extends Serializable, R> ResponseEntity<R> buildErrorResponse(ServiceAnswer.AnswerStatus status) {
        if (status == ServiceAnswer.AnswerStatus.NOT_FOUND)
            return ResponseEntity.notFound().build();

        if (status == ServiceAnswer.AnswerStatus.DELETED)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.internalServerError().build();
    }
}
