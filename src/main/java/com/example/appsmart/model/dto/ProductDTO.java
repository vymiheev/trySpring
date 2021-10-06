package com.example.appsmart.model.dto;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public final class ProductDTO extends BaseDTO {
    @NotNull
    private final String title;
    @Nullable
    private String description;
    @NotNull
    private final BigDecimal price;

    public ProductDTO(@NotNull UUID id, boolean isDeleted, @NotNull Instant createdAt, @Nullable Instant modifiedAt,
                      @NotNull String title, @Nullable String description, @NotNull BigDecimal price) {
        super(id, isDeleted, createdAt, modifiedAt);
        this.title = title;
        this.description = description;
        this.price = price;
    }

    @TestOnly
    public ProductDTO(@NotNull UUID id, @NotNull String title, @NotNull BigDecimal price) {
        super(id);
        this.title = title;
        this.price = price;
    }

    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

}