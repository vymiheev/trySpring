package com.example.appsmart.service;

import com.example.appsmart.converter.ConverterDTO2Customer;
import com.example.appsmart.model.Customer;
import com.example.appsmart.model.dto.CustomerDTO;
import com.example.appsmart.repository.CustomerRepository;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ConverterDTO2Customer converterDTO2Customer;

    public @NotNull ServiceAnswer<ArrayList<CustomerDTO>> getAllCustomers() {
        return wrapException(() -> {
            List<Customer> customers = customerRepository.findByIsDeletedOrderByCreatedAtAsc(false);
            final ArrayList<CustomerDTO> arrayList = customers.stream().map(it -> converterDTO2Customer.convert(it)).collect(Collectors.toCollection(ArrayList::new));
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, arrayList);
        });
    }

    public @NotNull ServiceAnswer<CustomerDTO> addNewCustomer() {
        return wrapException(() -> {
            Customer customer = new Customer(Instant.now(), "no title");
            customer = customerRepository.save(customer);
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Customer.convert(customer));
        });
    }

    public @NotNull ServiceAnswer<CustomerDTO> modifyCustomer(@NotNull UUID customerId) {
        return wrapException(() -> {
            final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            final ServiceAnswer<CustomerDTO> checkResult = check(optionalCustomer);
            if (!checkResult.isSuccess()) return checkResult;
            optionalCustomer.get().setModifiedAt(Instant.now());
            customerRepository.save(optionalCustomer.get());
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Customer.convert(optionalCustomer.get()));
        });
    }

    public @NotNull ServiceAnswer<CustomerDTO> getCustomerById(@NotNull UUID customerId) {
        return wrapException(() -> {
            final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            final ServiceAnswer<CustomerDTO> checkResult = check(optionalCustomer);
            if (!checkResult.isSuccess()) return checkResult;
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Customer.convert(optionalCustomer.get()));
        });
    }

    @Transactional
    public @NotNull ServiceAnswer<Boolean> removeCustomerById(@NotNull UUID customerId) {
        return wrapException(() -> {
            final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            final ServiceAnswer<CustomerDTO> checkResult = check(optionalCustomer);
            if (!checkResult.isSuccess())
                return new ServiceAnswer<>(checkResult.getStatus());

            //customerRepository.deleteById(UUID.fromString(customerId));
            optionalCustomer.get().setDeleted(true);
            customerRepository.save(optionalCustomer.get());
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS);
        });
    }

    @NotNull
    public ServiceAnswer<CustomerDTO> check(Optional<Customer> optionalCustomer) {
        if (optionalCustomer.isEmpty()) {
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.NOT_FOUND);
        }
        if (optionalCustomer.get().getDeleted()) {
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.DELETED);
        }
        return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Customer.convert(optionalCustomer.get()));
    }

    private <T extends Serializable> ServiceAnswer<T> wrapException(@NotNull Supplier<ServiceAnswer<T>> supplier) {
        ServiceAnswer<T> result;
        try {
            result = supplier.get();
        } catch (Throwable throwable) {
            log.error("Exception in CustomerService.", throwable);
            result = new ServiceAnswer<>(ServiceAnswer.AnswerStatus.ERROR, throwable);
        }
        return result;
    }
}
