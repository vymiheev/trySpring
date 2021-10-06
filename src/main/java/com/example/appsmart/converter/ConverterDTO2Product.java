package com.example.appsmart.converter;

import com.example.appsmart.model.Product;
import com.example.appsmart.model.dto.ProductDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ConverterDTO2Product implements Converter<Product, ProductDTO> {

    @Override
    @NotNull
    public ProductDTO convert(@NotNull Product product) {
        return new ProductDTO(product.getId(), product.getDeleted(), product.getCreatedAt(), product.getModifiedAt(),
                product.getTitle(), product.getDescription(), product.getPrice());
    }
}
