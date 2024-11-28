package com.test.account.converter;

import com.test.account.domain.Account;
import com.test.account.model.AccountDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {


    public AccountDTO toAccountDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountType(account.getAccountType());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setInitialBalance(account.getInitialBalance());
        dto.setCustomerId(account.getCustomerId());
        dto.setStatus(account.getStatus());

        if(account.getMovements() != null) {
            dto.setMovements(new MovementConverter().fromListEntityToDTOList(account.getMovements()));
        }
        return dto;
    }

    public Account fromDTO(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setInitialBalance(accountDTO.getInitialBalance());
        account.setCustomerId(accountDTO.getCustomerId());
        account.setStatus(accountDTO.getStatus());

        if(accountDTO.getMovements() != null) {
            account.setMovements(new MovementConverter().fromListDTOToEntityList(accountDTO.getMovements()));
        }

        return account;
    }

    public List<AccountDTO> fromListEntityToDTOList(List<Account> accounts) {
        return accounts.stream().map(this::toAccountDTO).collect(Collectors.toList());
    }

}
