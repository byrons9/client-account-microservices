package com.test.account.service;

import com.test.account.model.AccountDTO;
import com.test.account.model.CustomerResponse;
import com.test.account.model.MovementDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

public class ReportServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerHttpService customerHttpService;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovementsByDateClientName(){
        String startDateStr = "2021-01-01T00:00:00Z";
        String endDateStr = "2021-01-01T00:00:00Z";
        String customerName = "John Doe";

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe");

        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setId(1L);
        movementDTO.setMovementType("DEPOSIT");
        movementDTO.setAmount(100.0);
        movementDTO.setBalance(100.0);
        movementDTO.setStatus(true);
        movementDTO.setAccountNumber("123456");
        movementDTO.setCreatedAt(LocalDate.now());



        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setAccountNumber("123456");
        accountDTO.setInitialBalance(100.0);
        accountDTO.setCustomerId(1L);
        accountDTO.setStatus(true);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setMovements(List.of(movementDTO));




        Mockito.when(customerHttpService.getCustomerByName(customerName)).thenReturn(customerResponse);
        Mockito.when(accountService.findByCustomerId(customerResponse.getId())).thenReturn(List.of(accountDTO));
        reportService.getMovementsByDateClientName(startDateStr, endDateStr, customerName);




    }
}
