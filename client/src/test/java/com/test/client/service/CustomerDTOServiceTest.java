package com.test.client.service;

import com.test.client.component.CustomerConverter;
import com.test.client.domain.Customer;
import com.test.client.exception.CustomerNotFoundException;
import com.test.client.model.CustomerDTO;
import com.test.client.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

class CustomerDTOServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMock;

    @Mock
    private CustomerConverter customerConverterMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");
        customer2.setIdentification("0987654321");
        customer2.setAddress("456 Main St, Anytown USA");
        customer2.setPhone("098-765-4321");
        customer2.setGender("F");
        customer2.setAge(25);
        customer2.setStatus(true);
        customer2.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setName("Jane Doe");
        customerDTO2.setIdentification("0987654321");
        customerDTO2.setAddress("456 Main St, Anytown USA");
        customerDTO2.setPhone("098-765-4321");
        customerDTO2.setGender("F");
        customerDTO2.setAge(25);
        customerDTO2.setStatus(true);
        customerDTO2.setPassword("password");

        List<Customer> expectedCustomers = List.of(customer, customer2);
        List<CustomerDTO> expectedCustomersDTO = List.of(customerDTO, customerDTO2);

        Mockito.when(customerRepositoryMock.findAll()).thenReturn(expectedCustomers);
        Mockito.when(customerConverterMock.convertListEntityToDTO(Arrays.asList(customer, customer2))).thenReturn(Arrays.asList(customerDTO, customerDTO2));

        List<CustomerDTO> response = customerService.getAllCustomers();

        Assertions.assertThat(response).isEqualTo(expectedCustomersDTO).hasSize(2);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).findAll();
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(customer));
        Mockito.when(customerConverterMock.convertToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO response = customerService.getCustomerById(1L);

        Assertions.assertThat(response).isEqualTo(customerDTO);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).findById(1L);
    }

    @Test
    void TestShouldThrowExceptionWhenCustomerNotFound() {
        Mockito.when(customerRepositoryMock.findById(1L)).thenReturn(java.util.Optional.empty());
        Assertions.assertThatThrownBy(() -> customerService.getCustomerById(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer not found with id: 1");
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).findById(1L);
    }

    @Test
    void getCustomerByName(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerRepositoryMock.findByName("John Doe")).thenReturn(java.util.Optional.of(customer));
        Mockito.when(customerConverterMock.convertToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO response = customerService.getCustomerByName("John Doe");

        Assertions.assertThat(response).isEqualTo(customerDTO);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).findByName("John Doe");
    }

    @Test
    void TestShouldThrowExceptionWhenCustomerByNameNotFound() {
        Mockito.when(customerRepositoryMock.findByName("John Doe")).thenReturn(java.util.Optional.empty());
        Assertions.assertThatThrownBy(() -> customerService.getCustomerByName("John Doe"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer not found with name: John Doe");
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).findByName("John Doe");
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerConverterMock.convertToEntity(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepositoryMock.save(customer)).thenReturn(customer);
        Mockito.when(customerConverterMock.convertToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO response = customerService.createCustomer(customerDTO);

        Assertions.assertThat(response).isEqualTo(customerDTO);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).save(customer);
    }

    @Test
    void TestShouldAnExceptionWhenCreateFails(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerConverterMock.convertToEntity(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepositoryMock.save(customer)).thenThrow(new RuntimeException());

        Assertions.assertThatThrownBy(() -> customerService.createCustomer(customerDTO))
                .isInstanceOf(RuntimeException.class);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).save(customer);
    }

    @Test
    void TestShouldUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerRepositoryMock.existsById(1L)).thenReturn(true);
        Mockito.when(customerRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(customer));
        Mockito.when(customerConverterMock.foundCustomerFromDto(customer, customerDTO)).thenReturn(customer);
        Mockito.when(customerRepositoryMock.save(customer)).thenReturn(customer);
        Mockito.when(customerConverterMock.convertToDTO(customer)).thenReturn(customerDTO);

        CustomerDTO response = customerService.updateCustomer(1L, customerDTO);

        Assertions.assertThat(response).isEqualTo(customerDTO);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).save(customer);
    }

    @Test
    void TestShouldThrowAnExceptionCustomerNotFound(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerRepositoryMock.existsById(1L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> customerService.updateCustomer(1L, customerDTO))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer not found with id: 1");

        Mockito.verify(customerRepositoryMock, Mockito.times(1)).existsById(1L);
        Mockito.verify(customerRepositoryMock, Mockito.times(0)).save(customer);
    }

    @Test
    void TestShouldThrowAnExceptionWhenUpdateFails(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setIdentification("1234567890");
        customer.setAddress("123 Main St, Anytown USA");
        customer.setPhone("123-456-7890");
        customer.setGender("M");
        customer.setAge(30);
        customer.setStatus(true);
        customer.setPassword("password");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");
        customerDTO.setIdentification("1234567890");
        customerDTO.setAddress("123 Main St, Anytown USA");
        customerDTO.setPhone("123-456-7890");
        customerDTO.setGender("M");
        customerDTO.setAge(30);
        customerDTO.setStatus(true);
        customerDTO.setPassword("password");

        Mockito.when(customerRepositoryMock.existsById(1L)).thenReturn(true);
        Mockito.when(customerRepositoryMock.findById(1L)).thenReturn(java.util.Optional.of(customer));
        Mockito.when(customerConverterMock.foundCustomerFromDto(customer, customerDTO)).thenReturn(customer);
        Mockito.when(customerRepositoryMock.save(customer)).thenThrow(new RuntimeException());

        Assertions.assertThatThrownBy(() -> customerService.updateCustomer(1L, customerDTO))
                .isInstanceOf(RuntimeException.class);

        Mockito.verify(customerRepositoryMock, Mockito.times(1)).save(customer);
    }

    @Test
    void TestShouldDeleteCustomerById() {
        Mockito.when(customerRepositoryMock.existsById(1L)).thenReturn(true);
        customerService.deleteCustomerById(1L);
        Mockito.verify(customerRepositoryMock, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void TestShouldThrowAnExceptionWhenCustomerToDeleteNotFound(){
        Mockito.when(customerRepositoryMock.existsById(1L)).thenReturn(false);
        Assertions.assertThatThrownBy(() -> customerService.deleteCustomerById(1L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer not found with id: 1");
        Mockito.verify(customerRepositoryMock, Mockito.times(0)).deleteById(1L);
    }

}
