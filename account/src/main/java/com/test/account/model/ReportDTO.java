package com.test.account.model;

import lombok.Data;

@Data
public class ReportDTO {
    private String date;
    private String customer;
    private String accountNumber;
    private String type;
    private Double initialBalance;
    private Boolean status;
    private Double movement;
    private Double availableBalance;
}
