package com.example.appsmart.model.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class BaseDTO implements Serializable {
    @NotNull
    private final UUID id;
    private final boolean isDeleted;
    @NotNull
    private final Instant createdAt;
    @Nullable
    private final Instant modifiedAt;

    public BaseDTO(@NotNull UUID id, boolean isDeleted, @NotNull Instant createdAt, @Nullable Instant modifiedAt) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @TestOnly
    public BaseDTO(@NotNull UUID id) {
        this(id, false, Instant.now(), null);
    }

    public @NotNull UUID getId() {
        return id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public @NotNull Instant getCreatedAt() {
        return createdAt;
    }

    public @Nullable Instant getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return id.equals(baseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
