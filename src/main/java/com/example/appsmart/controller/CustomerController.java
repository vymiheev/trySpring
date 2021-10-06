package com.example.appsmart.controller;

import com.example.appsmart.model.dto.CustomerDTO;
import com.example.appsmart.service.CustomerService;
import com.example.appsmart.service.answer.ServiceAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/api/v1")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public @ResponseBody
    ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        final ServiceAnswer<ArrayList<CustomerDTO>> serviceAnswer = customerService.getAllCustomers();
        if (serviceAnswer.isSuccess())
            return ResponseEntity.ok(serviceAnswer.getObj());
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<CustomerDTO> addNewCustomer() {
        final ServiceAnswer<CustomerDTO> serviceAnswer = customerService.addNewCustomer();
        if (serviceAnswer.isSuccess())
            return ResponseEntity.ok(serviceAnswer.getObj());
        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> modifyCustomer(@PathVariable UUID customerId) {
        final ServiceAnswer<CustomerDTO> serviceAnswer = customerService.modifyCustomer(customerId);
        return ControllerTools.buildNoContentResponse(serviceAnswer);
    }

    @GetMapping("/customers/{customerId}")
    public @ResponseBody
    ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID customerId) {
        final ServiceAnswer<CustomerDTO> serviceAnswer = customerService.getCustomerById(customerId);
        return ControllerTools.buildEntityResponse(serviceAnswer);
    }

    @DeleteMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> removeCustomerById(@PathVariable UUID customerId) {
        final ServiceAnswer<Boolean> serviceAnswer = customerService.removeCustomerById(customerId);
        return ControllerTools.buildNoContentResponse(serviceAnswer);
    }
}
