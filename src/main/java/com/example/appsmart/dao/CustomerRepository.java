package com.example.appsmart.dao;

import com.example.appsmart.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    List<Customer> findByIsDeletedOrderByCreatedAtAsc(boolean isDeleted);
}
