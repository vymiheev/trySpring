package com.example.appsmart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "customer")
@Entity
public final class Customer extends BaseEntity {
    @Column(name = "title", nullable = false)
    @NotNull
    private String title = "(empty)";
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @NotNull
    private List<Product> products = new ArrayList<>();

    public Customer() {
    }

    public Customer(@NotNull Instant createdAt, @NotNull String title) {
        super(createdAt);
        this.title = title;
    }

    @TestOnly
    public Customer(@NotNull UUID id, @NotNull String title) {
        super(id);
        this.title = title;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public @NotNull List<Product> getProducts() {
        return products;
    }
}