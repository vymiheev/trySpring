package com.example.appsmart.converter;

import com.example.appsmart.model.Customer;
import com.example.appsmart.model.dto.CustomerDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ConverterDTO2Customer implements Converter<Customer, CustomerDTO> {

    @Override
    @NotNull
    public CustomerDTO convert(@NotNull Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getDeleted(), customer.getCreatedAt(), customer.getModifiedAt(), customer.getTitle());
    }
}
