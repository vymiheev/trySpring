package com.example.appsmart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@Table(name = "customer")
@Entity
public final class Customer extends CommonEntity {
    @Column(name = "title", nullable = false)
    @NotNull
    private String title = "(empty)";
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @NotNull
    private List<Product> products = new ArrayList<>();

    public Customer() {
    }

    public Customer(@NotNull UUID id, boolean isDeleted, @NotNull String title) {
        super(id, isDeleted);
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