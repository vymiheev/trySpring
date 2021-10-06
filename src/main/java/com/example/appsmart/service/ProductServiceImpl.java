package com.example.appsmart.service;

import com.example.appsmart.converter.ConverterDTO2Product;
import com.example.appsmart.model.Customer;
import com.example.appsmart.model.Product;
import com.example.appsmart.model.dto.CustomerDTO;
import com.example.appsmart.model.dto.ProductDTO;
import com.example.appsmart.repository.CustomerRepository;
import com.example.appsmart.repository.ProductRepository;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ConverterDTO2Product converterDTO2Product;
    @Autowired
    private CustomerService customerService;

    public @NotNull ServiceAnswer<ArrayList<ProductDTO>> getCustomersProducts(@NotNull UUID customerId) {
        return wrapException(() -> {
            final ServiceAnswer<CustomerDTO> serviceAnswer = customerService.getCustomerById(customerId);
            if (!serviceAnswer.isSuccess())
                return new ServiceAnswer<>(serviceAnswer.getStatus());

            final List<Product> products = productRepository.findByCustomerIdAndIsDeletedOrderByCreatedAtAsc(customerId, false);
            final ArrayList<ProductDTO> arrayList = products.stream().map(it -> converterDTO2Product.convert(it)).collect(Collectors.toCollection(ArrayList::new));
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, arrayList);
        });
    }

    @Transactional
    @NotNull
    public ServiceAnswer<ProductDTO> createNewProduct(@NotNull UUID customerId) {
        return wrapException(() -> {
            final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
            final ServiceAnswer<CustomerDTO> checkResult = customerService.check(optionalCustomer);
            if (!checkResult.isSuccess()) return new ServiceAnswer<>(checkResult.getStatus());

            Product product = new Product(Instant.now(), "new product", "description", BigDecimal.ZERO);
            product.setCustomer(optionalCustomer.get());
            product = productRepository.save(product);
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Product.convert(product));
        });
    }

    @Transactional
    @NotNull
    public ServiceAnswer<Boolean> removeProductById(@NotNull UUID productId) {
        return wrapException(() -> {
            final Optional<Product> optionalProduct = productRepository.findById(productId);
            final ServiceAnswer<ProductDTO> checkResult = check(optionalProduct);
            if (!checkResult.isSuccess())
                return new ServiceAnswer<>(checkResult.getStatus());

            //productRepository.deleteById(UUID.fromString(productId));
            optionalProduct.get().setDeleted(true);
            productRepository.save(optionalProduct.get());
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS);
        });
    }


    @NotNull
    public ServiceAnswer<ProductDTO> getProductById(@NotNull UUID productId) {
        return wrapException(() -> {
            final Optional<Product> optionalProduct = productRepository.findById(productId);
            return check(optionalProduct);
        });
    }

    @Transactional
    @NotNull
    public ServiceAnswer<ProductDTO> modifyProduct(@NotNull UUID productId) {
        return wrapException(() -> {
            final Optional<Product> optionalProduct = productRepository.findById(productId);
            final ServiceAnswer<ProductDTO> checkResult = check(optionalProduct);
            if (!checkResult.isSuccess())
                return new ServiceAnswer<>(checkResult.getStatus());

            optionalProduct.get().setModifiedAt(Instant.now());
            final Product product = productRepository.save(optionalProduct.get());
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Product.convert(product));
        });
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @NotNull
    private ServiceAnswer<ProductDTO> check(Optional<Product> optionalProduct) {
        if (optionalProduct.isEmpty()) {
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.NOT_FOUND);
        }
        if (optionalProduct.get().getDeleted()) {
            return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.DELETED, converterDTO2Product.convert(optionalProduct.get()));
        }
        return new ServiceAnswer<>(ServiceAnswer.AnswerStatus.SUCCESS, converterDTO2Product.convert(optionalProduct.get()));
    }

    private <T extends Serializable> ServiceAnswer<T> wrapException(@NotNull Supplier<ServiceAnswer<T>> supplier) {
        ServiceAnswer<T> result;
        try {
            result = supplier.get();
        } catch (Throwable throwable) {
            log.error("Exception in ProductService.", throwable);
            result = new ServiceAnswer<>(ServiceAnswer.AnswerStatus.ERROR, throwable);
        }
        return result;
    }

}
