package com.example.appsmart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class CommonEntity {
    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    private UUID id = UUID.randomUUID();
    @Column(name = "is_deleted", nullable = false)
    @JsonIgnore
    private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private Instant createdAt = Instant.now();

    @Column(name = "modified_at")
    @Nullable
    private Instant modifiedAt;

    public CommonEntity() {
    }

    public CommonEntity(@NotNull UUID id, @NotNull Boolean isDeleted) {
        this.id = id;
        this.isDeleted = isDeleted;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public @NotNull Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull Instant createdAt) {
        this.createdAt = createdAt;
    }

    public @Nullable Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(@Nullable Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonEntity that = (CommonEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
