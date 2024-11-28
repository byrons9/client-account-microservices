package com.test.client.service;

import com.test.client.component.CustomerConverter;
import com.test.client.domain.Customer;
import com.test.client.exception.CustomerNotFoundException;
import com.test.client.model.CustomerDTO;
import com.test.client.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{
    private static final  String NOT_FOUND_MESSAGE = "Customer not found with id: ";
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerConverter customerConverter, MessageSource messageSource) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return this.customerConverter.convertListEntityToDTO(customers);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = this.customerRepository.findById(id);
        return customer.map(customerConverter::convertToDTO).orElseThrow(() -> new CustomerNotFoundException(
                NOT_FOUND_MESSAGE + id)
        );
    }

    public CustomerDTO getCustomerByName(String clientName) {
        Optional.ofNullable(clientName).orElseThrow(() -> new IllegalArgumentException("Name is required"));
        Optional<Customer> client = this.customerRepository.findByName(clientName);
        return client.map(customerConverter::convertToDTO).orElseThrow(() -> new CustomerNotFoundException(
                "Customer not found with name: " + clientName)
        );
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        Customer customerToSave = this.customerConverter.convertToEntity(customer);
        Customer customerSaved = this.customerRepository.save(customerToSave);
        return this.customerConverter.convertToDTO(customerSaved);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        if (!this.customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(NOT_FOUND_MESSAGE + id);
        }

        Customer customerFound = this.customerRepository.findById(id).get();
        Customer customerToSave = this.customerConverter.foundCustomerFromDto(customerFound, customer);
        Customer customerSaved = this.customerRepository.save(customerToSave);
        return this.customerConverter.convertToDTO(customerSaved);

    }

    @Override
    public void deleteCustomerById(Long id) {
        if (!this.customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(NOT_FOUND_MESSAGE + id);
        }
        this.customerRepository.deleteById(id);
    }
}
