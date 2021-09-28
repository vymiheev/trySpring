package com.example.appsmart.dao;

import com.example.appsmart.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    List<Product> findByCustomerIdAndIsDeletedOrderByCreatedAtAsc(UUID customerId, boolean isDeleted);
}
