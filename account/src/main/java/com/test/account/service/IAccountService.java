package com.test.account.service;

import com.test.account.model.AccountDTO;

import java.util.List;

public interface IAccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    List<AccountDTO> findByCustomerId(Long clientId);
    AccountDTO getByAccountNumber(String accountNumber);
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
    void deleteAccount(Long id);
    Double getBalance(String accountNumber);
}
