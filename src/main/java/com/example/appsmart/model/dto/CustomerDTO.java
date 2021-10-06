package com.example.appsmart.model.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.time.Instant;
import java.util.UUID;

public final class CustomerDTO extends BaseDTO {
    @NotNull
    private final String title;

    public CustomerDTO(@NotNull UUID id, boolean isDeleted, @NotNull Instant createdAt, @Nullable Instant modifiedAt,
                       @NotNull String title) {
        super(id, isDeleted, createdAt, modifiedAt);
        this.title = title;
    }

    @TestOnly
    public CustomerDTO(@NotNull UUID id, @NotNull String title) {
        super(id);
        this.title = title;
    }

    public @NotNull String getTitle() {
        return title;
    }

}