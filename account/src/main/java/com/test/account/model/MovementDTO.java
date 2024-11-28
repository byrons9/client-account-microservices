package com.test.account.model;

import lombok.Data;

import java.time.Instant;

@Data
public class MovementDTO {
    private Long id;
    private String movementType;
    private Double amount;
    private Double balance;
    private Long accountId;
    private String accountNumber;
    private String movementDetail;
    private AccountDTO account;
    private Instant createdAt;
    private Boolean status;
}
