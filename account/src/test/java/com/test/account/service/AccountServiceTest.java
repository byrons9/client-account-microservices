package com.test.account.service;

import com.test.account.converter.AccountConverter;
import com.test.account.domain.Account;
import com.test.account.exception.AccountNotFoundException;
import com.test.account.model.AccountDTO;
import com.test.account.model.CustomerResponse;
import com.test.account.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountConverter accountConverter;

    @Mock
    private ICustomerHttpService customerHttpService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setAccountNumber("123457");
        accountDTO2.setCustomerId(2L);
        accountDTO2.setCustomerName("Jane Doe");
        accountDTO2.setAccountType("CURRENT");
        accountDTO2.setInitialBalance(2000.0);
        accountDTO2.setStatus(true);
        accountDTO2.setMovements(null);

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Account account2 = new Account();
        account2.setAccountNumber("123457");
        account2.setCustomerId(2L);
        account2.setAccountType("CURRENT");
        account2.setInitialBalance(2000.0);
        account2.setStatus(true);
        account2.setMovements(null);

        List<AccountDTO> expectedAccountDTOList = List.of(accountDTO, accountDTO2);

        Mockito.when(accountRepository.findAll()).thenReturn(List.of(account, account2));
        Mockito.when(accountConverter.fromListEntityToDTOList(List.of(account, account2))).thenReturn(List.of(accountDTO, accountDTO2));

        List<AccountDTO> accountDTOList = accountService.getAllAccounts();

        Assertions.assertThat(accountDTOList).isEqualTo(expectedAccountDTOList).hasSize(2);

        Mockito.verify(accountRepository, Mockito.times(1)).findAll();

    }

    @Test
    void testGetAccountById() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Mockito.when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));
        Mockito.when(accountConverter.toAccountDTO(account)).thenReturn(accountDTO);

        AccountDTO accountDTOFound = accountService.getAccountById(1L);

        Assertions.assertThat(accountDTOFound).isEqualTo(accountDTO);

        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testShouldThrowExceptionWhenAccountNotFound() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.getAccountById(1L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account not found with id: 1");

        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testFindByCustomerId() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        AccountDTO accountDTO2 = new AccountDTO();
        accountDTO2.setAccountNumber("123457");
        accountDTO2.setCustomerId(1L);
        accountDTO2.setCustomerName("John Doe");
        accountDTO2.setAccountType("CURRENT");
        accountDTO2.setInitialBalance(2000.0);
        accountDTO2.setStatus(true);
        accountDTO2.setMovements(null);

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Account account2 = new Account();
        account2.setAccountNumber("123457");
        account2.setCustomerId(1L);
        account2.setAccountType("CURRENT");
        account2.setInitialBalance(2000.0);
        account2.setStatus(true);
        account2.setMovements(null);

        List<AccountDTO> expectedAccountDTOList = List.of(accountDTO, accountDTO2);

        Mockito.when(accountRepository.findByClientId(1L)).thenReturn(List.of(account, account2));
        Mockito.when(accountConverter.fromListEntityToDTOList(List.of(account, account2))).thenReturn(List.of(accountDTO, accountDTO2));

        List<AccountDTO> accountDTOList = accountService.findByCustomerId(1L);

        Assertions.assertThat(accountDTOList).isEqualTo(expectedAccountDTOList).hasSize(2);

        Mockito.verify(accountRepository, Mockito.times(1)).findByClientId(1L);
    }

    @Test
    void testGetByAccountNumber() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Mockito.when(accountRepository.findByAccountNumber("123456")).thenReturn(java.util.Optional.of(account));
        Mockito.when(accountConverter.toAccountDTO(account)).thenReturn(accountDTO);

        AccountDTO accountDTOFound = accountService.getByAccountNumber("123456");

        Assertions.assertThat(accountDTOFound).isEqualTo(accountDTO);

        Mockito.verify(accountRepository, Mockito.times(1)).findByAccountNumber("123456");
    }

    @Test
    void testShouldThrowExceptionWhenAccountNumberNotFound() {
        Mockito.when(accountRepository.findByAccountNumber("123456")).thenReturn(java.util.Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.getByAccountNumber("123456"))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account not found with account number: 123456");

        Mockito.verify(accountRepository, Mockito.times(1)).findByAccountNumber("123456");
    }

    @Test
    void testCreateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe");

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Mockito.when(customerHttpService.getCustomerByName("John Doe")).thenReturn(customerResponse);
        Mockito.when(accountConverter.fromDTO(accountDTO)).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountConverter.toAccountDTO(account)).thenReturn(accountDTO);

        AccountDTO accountDTOCreated = accountService.createAccount(accountDTO);

        Assertions.assertThat(accountDTOCreated).isEqualTo(accountDTO);

        Mockito.verify(customerHttpService, Mockito.times(1)).getCustomerByName("John Doe");
        Mockito.verify(accountConverter, Mockito.times(1)).fromDTO(accountDTO);
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
        Mockito.verify(accountConverter, Mockito.times(1)).toAccountDTO(account);
    }

    @Test
    void TestShouldThrowWhenClientNameIsNull() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerName(null);
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        Assertions.assertThatThrownBy(() -> accountService.createAccount(accountDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Client name is required");

        Mockito.verify(customerHttpService, Mockito.never()).getCustomerByName(null);
        Mockito.verify(accountConverter, Mockito.never()).fromDTO(accountDTO);
        Mockito.verify(accountRepository, Mockito.never()).save(null);
        Mockito.verify(accountConverter, Mockito.never()).toAccountDTO(null);
    }

    @Test
    void testUpdateAccount() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(null);

        Mockito.when(accountRepository.existsById(1L)).thenReturn(true);
        Mockito.when(accountConverter.fromDTO(accountDTO)).thenReturn(account);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountConverter.toAccountDTO(account)).thenReturn(accountDTO);

        AccountDTO accountDTOUpdated = accountService.updateAccount(1L, accountDTO);

        Assertions.assertThat(accountDTOUpdated).isEqualTo(accountDTO);

        Mockito.verify(accountRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(accountConverter, Mockito.times(1)).fromDTO(accountDTO);
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
        Mockito.verify(accountConverter, Mockito.times(1)).toAccountDTO(account);
    }

    @Test
    void testShouldThrowExceptionWhenAccountNotFoundForUpdate() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        accountDTO.setCustomerId(1L);
        accountDTO.setCustomerName("John Doe");
        accountDTO.setAccountType("SAVINGS");
        accountDTO.setInitialBalance(1000.0);
        accountDTO.setStatus(true);
        accountDTO.setMovements(null);

        Mockito.when(accountRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> accountService.updateAccount(1L, accountDTO))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account not found with id: 1");

        Mockito.verify(accountRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(accountConverter, Mockito.never()).fromDTO(accountDTO);
        Mockito.verify(accountRepository, Mockito.never()).save(null);
        Mockito.verify(accountConverter, Mockito.never()).toAccountDTO(null);
    }

    @Test
    void testDeleteAccount() {
        Mockito.when(accountRepository.existsById(1L)).thenReturn(true);

        accountService.deleteAccount(1L);

        Mockito.verify(accountRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testShouldThrowExceptionWhenAccountNotFoundForDelete() {
        Mockito.when(accountRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> accountService.deleteAccount(1L))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account not found with id: 1");

        Mockito.verify(accountRepository, Mockito.times(1)).existsById(1L);
        Mockito.verify(accountRepository, Mockito.never()).deleteById(1L);
    }

    @Test
    void testGetBalance() {
        Account account = new Account();
        account.setAccountNumber("123456");
        account.setCustomerId(1L);
        account.setAccountType("SAVINGS");
        account.setInitialBalance(1000.0);
        account.setStatus(true);
        account.setMovements(Collections.emptyList());

        Mockito.when(accountRepository.findByAccountNumber("123456")).thenReturn(java.util.Optional.of(account));

        Double balance = accountService.getBalance("123456");

        Assertions.assertThat(balance).isEqualTo(1000.0);

        Mockito.verify(accountRepository, Mockito.times(1)).findByAccountNumber("123456");
    }

    @Test
    void testShouldThrowExceptionWhenAccountNumberNotFoundForBalance() {
        Mockito.when(accountRepository.findByAccountNumber("123456")).thenReturn(java.util.Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.getBalance("123456"))
                .isInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account not found with account number: 123456");

    }



}
