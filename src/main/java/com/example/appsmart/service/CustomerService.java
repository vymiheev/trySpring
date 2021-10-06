package com.example.appsmart.service;

import com.example.appsmart.model.Customer;
import com.example.appsmart.model.dto.CustomerDTO;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    @NotNull
    ServiceAnswer<ArrayList<CustomerDTO>> getAllCustomers();

    @NotNull
    ServiceAnswer<CustomerDTO> addNewCustomer();

    @NotNull
    ServiceAnswer<CustomerDTO> modifyCustomer(@NotNull UUID customerId);

    @NotNull
    ServiceAnswer<CustomerDTO> getCustomerById(@NotNull UUID customerId);

    @NotNull
    ServiceAnswer<Boolean> removeCustomerById(@NotNull UUID customerId);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @NotNull
    ServiceAnswer<CustomerDTO> check(Optional<Customer> optionalCustomer);
}
