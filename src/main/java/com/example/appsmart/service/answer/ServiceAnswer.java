package com.example.appsmart.service.answer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class ServiceAnswer<T extends Serializable> implements Serializable {
    @NotNull
    private final AnswerStatus status;
    @Nullable
    private T obj;
    @Nullable
    private Throwable error;

    public ServiceAnswer(@NotNull AnswerStatus status) {
        this.status = status;
    }

    public ServiceAnswer(@NotNull AnswerStatus status, @NotNull T obj) {
        this.status = status;
        this.obj = obj;
    }

    public ServiceAnswer(@NotNull AnswerStatus status, @NotNull Throwable error) {
        this.status = status;
        this.error = error;
    }

    public @NotNull AnswerStatus getStatus() {
        return status;
    }

    public @Nullable T getObj() {
        return obj;
    }

    public void setObj(@NotNull T obj) {
        this.obj = obj;
    }

    public @Nullable Throwable getError() {
        return error;
    }

    public void setError(@NotNull Throwable error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return status == AnswerStatus.SUCCESS;
    }

    public enum AnswerStatus {
        CREATED, DELETED, NOT_FOUND, SUCCESS, ERROR
    }
}
