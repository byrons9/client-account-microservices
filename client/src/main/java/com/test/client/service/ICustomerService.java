package com.test.client.service;

import com.test.client.model.CustomerDTO;

import java.util.List;

public interface ICustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO getCustomerByName(String identification);
    CustomerDTO createCustomer(CustomerDTO customer);
    CustomerDTO updateCustomer(Long id, CustomerDTO customer);
    void deleteCustomerById(Long id);

}
