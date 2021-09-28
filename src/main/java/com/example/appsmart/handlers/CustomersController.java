package com.example.appsmart.handlers;

import com.example.appsmart.dao.CustomerRepository;
import com.example.appsmart.dao.ProductRepository;
import com.example.appsmart.models.Customer;
import com.example.appsmart.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/api/v1")
public class CustomersController {
    private static final Logger log = LoggerFactory.getLogger(CustomersController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/customers")
    public @ResponseBody
    ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findByIsDeletedOrderByCreatedAtAsc(false));
    }

    @PostMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Customer> addNewCustomer() {
        final Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setCreatedAt(Instant.now());
        customer.setTitle("no title");
        customer.setDeleted(false);
        customerRepository.save(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> modifyCustomer(@PathVariable String customerId) {
        if (isNotValidUUID(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(customerId));
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalCustomer.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        optionalCustomer.get().setModifiedAt(Instant.now());
        customerRepository.save(optionalCustomer.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerId}")
    public @ResponseBody
    ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        if (isNotValidUUID(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(customerId));
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalCustomer.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalCustomer.get());
    }

    @DeleteMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> removeCustomerById(@PathVariable String customerId) {
        if (isNotValidUUID(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(customerId));
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalCustomer.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        //customerRepository.deleteById(UUID.fromString(customerId));
        optionalCustomer.get().setDeleted(true);
        customerRepository.save(optionalCustomer.get());
        return ResponseEntity.ok().build();
    }

    ////Product APIs

    @GetMapping("/customers/{customerId}/products")
    public @ResponseBody
    ResponseEntity<List<Product>> getCustomersProducts(@PathVariable String customerId) {
        if (isNotValidUUID(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(customerId));
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalCustomer.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        final List<Product> products = productRepository.findByCustomerIdAndIsDeletedOrderByCreatedAtAsc(UUID.fromString(customerId), false);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/customers/{customerId}/products")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Product> createNewProduct(@PathVariable String customerId) {
        if (isNotValidUUID(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Customer> optionalCustomer = customerRepository.findById(UUID.fromString(customerId));
        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalCustomer.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        Product product = new Product();
        product.setCustomer(optionalCustomer.get());
        product.setCreatedAt(Instant.now());
        product.setTitle("new product");
        product.setPrice(BigDecimal.ZERO);

        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> removeProductById(@PathVariable String productId) {
        if (isNotValidUUID(productId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(productId));
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalProduct.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        //productRepository.deleteById(UUID.fromString(productId));
        optionalProduct.get().setDeleted(true);
        productRepository.save(optionalProduct.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/{productId}")
    public @ResponseBody
    ResponseEntity<Product> getProductById(@PathVariable String productId) {
        if (isNotValidUUID(productId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(productId));
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalProduct.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Product> modifyProduct(@PathVariable String productId) {
        if (isNotValidUUID(productId)) {
            return ResponseEntity.badRequest().build();
        }
        final Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(productId));
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalProduct.get().getDeleted()) {
            return ResponseEntity.badRequest().build();
        }
        optionalProduct.get().setModifiedAt(Instant.now());
        productRepository.save(optionalProduct.get());
        return ResponseEntity.noContent().build();
    }


    private boolean isNotValidUUID(String customerId) {
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(customerId);
        } catch (IllegalArgumentException exception) {
            return true;
        }
        return false;
    }
}
