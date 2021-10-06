package com.example.appsmart.security.roles;

import java.util.Objects;

public class UserPrincipal {
    private final Integer id;
    private final String username;
    private final  boolean isAdmin;

    public UserPrincipal(Integer id, String username, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return isAdmin == that.isAdmin && Objects.equals(id, that.id) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, isAdmin);
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
