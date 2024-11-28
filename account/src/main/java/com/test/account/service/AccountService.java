package com.test.account.service;

import com.test.account.converter.AccountConverter;
import com.test.account.domain.Account;
import com.test.account.domain.Movement;
import com.test.account.exception.AccountNotFoundException;
import com.test.account.model.AccountDTO;
import com.test.account.model.CustomerResponse;
import com.test.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.    util.List;
import java.util.Optional;

@Service
public class AccountService implements  IAccountService{
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final ICustomerHttpService customerHttpService;


    @Autowired
    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter, ICustomerHttpService customerHttpService) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.customerHttpService = customerHttpService;
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountConverter.fromListEntityToDTOList(accounts);
    }

    public AccountDTO getAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(accountConverter::toAccountDTO).orElseThrow(() -> new AccountNotFoundException(
                "Account not found with id: " + id)
        );
    }

    public List<AccountDTO> findByCustomerId(Long clientId){
        List<Account> accounts = accountRepository.findByClientId(clientId);
        return accountConverter.fromListEntityToDTOList(accounts);
    }

    public AccountDTO getByAccountNumber(String accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.map(accountConverter::toAccountDTO).orElseThrow(() -> new AccountNotFoundException(
                "Account not found with account number: " + accountNumber)
        );
    }

    public AccountDTO createAccount(AccountDTO accountDTO){
        String clientName = accountDTO.getCustomerName();
        if (clientName == null) {
            throw new IllegalArgumentException("Client name is required");
        }
        CustomerResponse customerResponse = customerHttpService.getCustomerByName(accountDTO.getCustomerName());
        accountDTO.setCustomerId(customerResponse.getId());
        Account account = accountConverter.fromDTO(accountDTO);
        AccountDTO response = accountConverter.toAccountDTO(accountRepository.save(account));
        response.setCustomerName(customerResponse.getName());
        return response;
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        if (accountRepository.existsById(id)) {
            Account account = accountConverter.fromDTO(accountDTO);
            account.setId(id);
            return accountConverter.toAccountDTO(accountRepository.save(account));
        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
    }

    public void deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }
    }

    public Double getBalance(String accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);

        if(account.isEmpty()){
            throw new AccountNotFoundException("Account not found with account number: " + accountNumber);
        }

        List<Movement> movements = account.get().getMovements();
        Double balance = account.get().getInitialBalance();

        for(Movement movement : movements){
            balance += movement.getAmount();
        }

        return balance;
    }



}
