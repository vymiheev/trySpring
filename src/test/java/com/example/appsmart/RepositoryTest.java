package com.example.appsmart;

import com.example.appsmart.handlers.CustomersController;
import com.example.appsmart.models.Customer;
import com.example.appsmart.models.Product;
import com.example.appsmart.security.UserPrincipal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(
        locations = "classpath:application-test.properties")
@ActiveProfiles(value = "test")
public class RepositoryTest {

    @Autowired
    private CustomersController controller;
    private static List<Customer> dummyCustomers;
    @Autowired
    private MockMvc mockMvc;

    public static void init() {
        dummyCustomers = Arrays.asList(
                new Customer(UUID.fromString("475d9655-4630-4a8e-8b15-1f2d6849785b"), false, "title 1"),
                new Customer(UUID.fromString("988025e7-32c8-4a9c-a578-ff09681c8931"), false, "title 3"));
        final List<Product> dummyProductsA = List.of(
                new Product(UUID.fromString("cd8e82bf-2a8d-48c8-8d60-ace3a87a4a6b"), false, "product 1", "description of product 1", BigDecimal.valueOf(0.10)),
                new Product(UUID.fromString("007d9c55-c992-4d13-8a05-8947b2ba79cd"), false, "product 2", "description of product 2", BigDecimal.valueOf(10.10)),
                new Product(UUID.fromString("725e6f60-e0ed-4274-8409-aa6119c64b01"), false, "product 3", "description of product 3", BigDecimal.valueOf(50.10)));
        final List<Product> dummyProductsB = List.of(
                //new Product(UUID.fromString("f520fccb-893e-4ccd-b99d-cddbd4409a9b"), false, "product 5", "description of product 5", BigDecimal.valueOf(666.66)),
                new Product(UUID.fromString("678d4aea-1f73-11ec-9621-0242ac130002"), false, "product 6", "description of product 6", BigDecimal.valueOf(0.66)));
        dummyCustomers.get(0).getProducts().addAll(dummyProductsA);
        dummyCustomers.get(1).getProducts().addAll(dummyProductsB);
        for (Product product : dummyProductsA) {
            product.setCustomer(dummyCustomers.get(0));
        }
        for (Product product : dummyProductsB) {
            product.setCustomer(dummyCustomers.get(1));
        }
    }

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    private void withoutSecurity(Runnable runnable) {
        try {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(1, "", true), null,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

            runnable.run();
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    @Transactional
    public void findAvailableCustomers_withoutDeleted() {
        withoutSecurity(() -> {
            init();
            ResponseEntity<List<Customer>> customerResponseEntity = controller.getAllCustomers();
            Assertions.assertEquals(HttpStatus.OK, customerResponseEntity.getStatusCode());
            final List<Customer> customers = customerResponseEntity.getBody();
            Assertions.assertNotNull(customers);

            assertCustomers(customers);
            Assertions.assertEquals(2, customers.size());
            Assertions.assertEquals("title 1", customers.get(0).getTitle());
            Assertions.assertNull(customers.get(0).getModifiedAt());
            Assertions.assertFalse(customers.get(0).getDeleted());
        });
    }

    private void assertCustomers(List<Customer> customers) {
        Assertions.assertEquals(dummyCustomers.size(), customers.size());
        for (int it = 0; it < customers.size(); it++) {
            Customer dummyCustomer = dummyCustomers.get(it);
            Customer customer = customers.get(it);
            Assertions.assertEquals(dummyCustomer, customer);
            Assertions.assertEquals(dummyCustomer.getTitle(), customer.getTitle());
            Assertions.assertEquals(dummyCustomer.getDeleted(), customer.getDeleted());
            ResponseEntity<List<Product>> productEntity = controller.getCustomersProducts(customer.getId().toString());
            Assertions.assertEquals(HttpStatus.OK, productEntity.getStatusCode());
            Assertions.assertNotNull(productEntity.getBody());

            assertProducts(dummyCustomer.getProducts(), productEntity.getBody());
        }
    }

    private void assertProducts(List<Product> dummyProducts, List<Product> products) {
        Assertions.assertEquals(dummyProducts.size(), products.size());
        for (int it = 0; it < products.size(); it++) {
            Product dummyProduct = dummyProducts.get(it);
            Product product = products.get(it);
            Assertions.assertEquals(dummyProduct, product);
            Assertions.assertEquals(dummyProduct.getTitle(), product.getTitle());
            Assertions.assertEquals(dummyProduct.getDeleted(), product.getDeleted());
            Assertions.assertEquals(dummyProduct.getDescription(), product.getDescription());
            Assertions.assertEquals(0, dummyProduct.getPrice().compareTo(product.getPrice()));
            Assertions.assertEquals(dummyProduct.getCustomer(), product.getCustomer());
        }
    }

    @Test
    public void addNewCustomer() {
        try {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(1, "", true), null,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));

            Assertions.assertEquals(HttpStatus.OK, controller.addNewCustomer().getStatusCode());
            ResponseEntity<List<Customer>> customerResponseEntity = controller.getAllCustomers();
            Assertions.assertFalse(Objects.requireNonNull(customerResponseEntity.getBody()).isEmpty());
            Assertions.assertEquals(3, customerResponseEntity.getBody().size());
            Customer customer = customerResponseEntity.getBody().get(2);
            Assertions.assertNotEquals(Boolean.TRUE, customer.getDeleted());
            Assertions.assertNotNull(customer.getId());
            Assertions.assertNotNull(customer.getTitle());

            ResponseEntity<List<Product>> productEntity = controller.getCustomersProducts(customer.getId().toString());
            Assertions.assertEquals(HttpStatus.OK, productEntity.getStatusCode());
            Assertions.assertTrue(Objects.requireNonNull(productEntity.getBody()).isEmpty());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Test
    public void deleteNotExistedConsumer() {
        withoutSecurity(() -> {
            ResponseEntity<Void> responseEntity = controller.removeCustomerById(UUID.randomUUID().toString());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        });
    }

    @Test
    public void createNewProduct() {
        withoutSecurity(() -> {
            init();
            try {
                mockMvc.perform(post("/api/v1/customers/475d9655-4630-4a8e-8b15-1f2d6849785b/products"))
                        .andDo(print()).andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"title\":\"new product\",\"description\":null,\"price\":0,\"deleted\":false")));
            } catch (Exception e) {
                Assertions.fail(e);
            }
        });
    }

    //todo implement other cases
}