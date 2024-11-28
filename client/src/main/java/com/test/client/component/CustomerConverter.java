package com.test.client.component;

import com.test.client.domain.Customer;
import com.test.client.model.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    private final PasswordConverterComponent passwordConverterComponent;

    @Autowired
    public CustomerConverter(PasswordConverterComponent passwordConverterComponent) {
        this.passwordConverterComponent = passwordConverterComponent;
    }

    public CustomerDTO convertToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setAge(customer.getAge());
        customerDTO.setGender(customer.getGender());
        customerDTO.setIdentification(customer.getIdentification());
        customerDTO.setName(customer.getName());
        customerDTO.setPhone(customer.getPhone());
        // Avoid password for security
        customerDTO.setPassword(null);
        customerDTO.setStatus(customer.getStatus());
        return customerDTO;
    }

    public com.test.client.domain.Customer convertToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        com.test.client.domain.Customer customer = new com.test.client.domain.Customer();
        customer.setId(customerDTO.getId());
        customer.setAddress(customerDTO.getAddress());
        customer.setAge(customerDTO.getAge());
        customer.setGender(customer.getGender());
        customer.setIdentification(customer.getIdentification());
        customer.setName(customer.getName());
        customer.setPhone(customer.getPhone());
        // Encrypt the password before saving it to the database
        customer.setPassword(passwordConverterComponent.encodePassword(customerDTO.getPassword()));
        customer.setStatus(customer.getStatus());
        return customer;

    }

    public com.test.client.domain.Customer foundCustomerFromDto(com.test.client.domain.Customer customer, CustomerDTO customerDTO) {

        if(customer == null || customerDTO == null) {
            return null;
        }

        customer.setAge(customerDTO.getAge());
        customer.setAddress(customerDTO.getAddress());
        customer.setGender(customerDTO.getGender());
        customer.setIdentification(customerDTO.getIdentification());
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        customer.setStatus(customerDTO.getStatus());
        return customer;
    }

    public List<CustomerDTO> convertListEntityToDTO(List<com.test.client.domain.Customer> customers) {
        return customers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}

