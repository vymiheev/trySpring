package com.example.appsmart.controller;

import com.example.appsmart.model.dto.ProductDTO;
import com.example.appsmart.service.ProductService;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@Controller
@RequestMapping(path = "/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/customers/{customerId}/products")
    public @ResponseBody
    ResponseEntity<ArrayList<ProductDTO>> getCustomersProducts(@PathVariable UUID customerId) {
        final ServiceAnswer<ArrayList<ProductDTO>> serviceAnswer = productService.getCustomersProducts(customerId);
        return ControllerTools.buildEntityResponse(serviceAnswer);
    }

    @PostMapping("/customers/{customerId}/products")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<ProductDTO> createNewProduct(@PathVariable UUID customerId) {
        final ServiceAnswer<ProductDTO> serviceAnswer = productService.createNewProduct(customerId);
        return ControllerTools.buildEntityResponse(serviceAnswer);
    }

    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> removeProductById(@PathVariable UUID productId) {
        final ServiceAnswer<Boolean> serviceAnswer = productService.removeProductById(productId);
        return ControllerTools.buildNoContentResponse(serviceAnswer);
    }

    @GetMapping("/products/{productId}")
    public @ResponseBody
    ResponseEntity<ProductDTO> getProductById(@PathVariable UUID productId) {
        final ServiceAnswer<ProductDTO> serviceAnswer = productService.getProductById(productId);
        return ControllerTools.buildEntityResponse(serviceAnswer);
    }

    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<ProductDTO> modifyProduct(@PathVariable UUID productId) {
        final ServiceAnswer<ProductDTO> serviceAnswer = productService.modifyProduct(productId);
        return ControllerTools.buildEntityResponse(serviceAnswer);
    }
}
