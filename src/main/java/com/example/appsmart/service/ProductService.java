package com.example.appsmart.service;

import com.example.appsmart.model.dto.ProductDTO;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public interface ProductService {
    @NotNull
    ServiceAnswer<ArrayList<ProductDTO>> getCustomersProducts(@NotNull UUID customerId);

    @NotNull
    ServiceAnswer<ProductDTO> createNewProduct(@NotNull UUID customerId);

    @NotNull
    ServiceAnswer<Boolean> removeProductById(@NotNull UUID productId);

    @NotNull
    ServiceAnswer<ProductDTO> getProductById(@NotNull UUID productId);

    @NotNull
    ServiceAnswer<ProductDTO> modifyProduct(@NotNull UUID productId);
}
