package com.test.account.model;

import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {
    private Long id;
    private String accountType;
    private String accountNumber;
    private Double initialBalance;
    private Long customerId;
    private Boolean status;
    private String customerName;
    private List<MovementDTO> movements;
}
