package com.test.account.service;

import com.test.account.model.AccountDTO;
import com.test.account.model.CustomerResponse;
import com.test.account.model.MovementDTO;
import com.test.account.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {


    private final  AccountService accountService;
    private final CustomerHttpService customerHttpService;

    @Autowired
    public ReportService(AccountService accountService, CustomerHttpService customerHttpService) {
        this.accountService = accountService;
        this.customerHttpService = customerHttpService;
    }

    public List<ReportDTO> getMovementsByDateClientName(String startDateStr, String endDateStr, String customerName) {

        if (startDateStr == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (endDateStr == null) {
            throw new IllegalArgumentException("End date is required");
        }

        Instant startDate = this.convertStringToLocalInstant(startDateStr);
        Instant endDate = this.convertStringToLocalInstant(endDateStr);


        if (customerName == null) {
            throw new IllegalArgumentException("Client name is required");
        }
        CustomerResponse customerResponse = customerHttpService.getCustomerByName(customerName);

        if (customerResponse == null) {
            throw new IllegalArgumentException("Customer not found");
        }


        List<AccountDTO> accounts = accountService.findByCustomerId(customerResponse.getId());

        if (accounts.isEmpty()) {
            throw new IllegalArgumentException("Customer does not have accounts");
        }

        return this.getMovementsByDateClientName(startDate, endDate, accounts, customerResponse);
    }

    private List<ReportDTO> getMovementsByDateClientName(Instant startDate, Instant endDate, List<AccountDTO> accounts, CustomerResponse clientResponse) {
        List<ReportDTO> reports = new ArrayList<>();
        accounts.forEach(account -> {
            List<MovementDTO> movements = this.filterMovementsByDate(account.getMovements(), startDate, endDate);
            movements.forEach(movement -> {
                reports.add(this.convertData(movement, account, clientResponse));
            });
        });

        return  reports;
    }

    public List<MovementDTO> filterMovementsByDate(List<MovementDTO> movements, Instant startDate, Instant endDate) {
        return movements.stream()
                .filter(movement -> !movement.getCreatedAt().isBefore(startDate) && !movement.getCreatedAt().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private ReportDTO convertData(MovementDTO movementDTO, AccountDTO accountDTO, CustomerResponse customerResponse) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDate(castDate(movementDTO.getCreatedAt()));
        reportDTO.setCustomer(customerResponse.getName());
        reportDTO.setAccountNumber(accountDTO.getAccountNumber());
        reportDTO.setType(accountDTO.getAccountType());
        reportDTO.setInitialBalance(accountDTO.getInitialBalance());
        reportDTO.setStatus(movementDTO.getStatus());
        reportDTO.setMovement(movementDTO.getAmount());
        reportDTO.setAvailableBalance(movementDTO.getBalance());
        return reportDTO;
    }

    private String castDate(Instant date){
        return date.toString();
    }

    private Instant convertStringToLocalInstant(String dateStr){
        try{
            return Instant.parse(dateStr);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid date format");
        }

    }



}
