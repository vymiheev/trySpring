package com.example.appsmart.repository;

import com.example.appsmart.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findByCustomerIdAndIsDeletedOrderByCreatedAtAsc(UUID customerId, boolean isDeleted);
}
