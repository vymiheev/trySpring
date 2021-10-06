package com.example.appsmart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "product")
@Entity
public final class Product extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    @Nullable
    private Customer customer;

    @Column(name = "title", nullable = false)
    @NotNull
    private String title = "(empty)";

    @Column(name = "description", length = 1024)
    @Nullable
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    @NotNull
    private BigDecimal price = BigDecimal.ZERO;

    public Product() {
    }

    public Product(@NotNull Instant createdAt, @NotNull String title, @Nullable String description,
                   @NotNull BigDecimal price) {
        super(createdAt);
        this.title = title;
        this.description = description;
        this.price = price;
    }

    @TestOnly
    public Product(@NotNull UUID id, @NotNull String title, @Nullable String description, @NotNull BigDecimal price) {
        super(id);
        this.title = title;
        this.description = description;
        this.price = price;
    }

    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
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

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @Nullable
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(@NotNull Customer customer) {
        this.customer = customer;
    }

}