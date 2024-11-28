package com.test.account.service;

import com.test.account.model.AccountDTO;
import com.test.account.model.CustomerResponse;
import com.test.account.model.MovementDTO;
import com.test.account.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        LocalDate startDate = this.convertStringToLocalDate(startDateStr);
        LocalDate endDate = this.convertStringToLocalDate(endDateStr);


        if (customerName == null) {
            throw new IllegalArgumentException("Customer name is required");
        }
        CustomerResponse clientResponse = customerHttpService.getCustomerByName(customerName);

        if (clientResponse == null) {
            throw new IllegalArgumentException("Customer not found");
        }


        List<AccountDTO> accounts = accountService.findByCustomerId(clientResponse.getId());

        if (accounts.isEmpty()) {
            throw new IllegalArgumentException("Customer has no accounts");
        }

        return this.getMovementsByDateClientName(startDate, endDate, accounts, clientResponse);
    }

    private List<ReportDTO> getMovementsByDateClientName(LocalDate startDate, LocalDate endDate, List<AccountDTO> accounts, CustomerResponse customerResponse) {
        List<ReportDTO> reports = new ArrayList<>();
        accounts.forEach(account -> {
            List<MovementDTO> movements = this.filterMovementsByDate(account.getMovements(), startDate, endDate);
            movements.forEach(movement -> {
                reports.add(this.convertData(movement, account, customerResponse));
            });
        });

        return  reports;
    }

    public List<MovementDTO> filterMovementsByDate(List<MovementDTO> movements, LocalDate startDate, LocalDate endDate) {
        return movements.stream()
                .filter(movement -> !movement.getCreatedAt().isBefore(startDate) && !movement.getCreatedAt().isAfter(endDate))
                .collect(Collectors.toList());
    }

    private ReportDTO convertData(MovementDTO movementDTO, AccountDTO accountDTO, CustomerResponse clientResponse) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDate(castDate(movementDTO.getCreatedAt()));
        reportDTO.setCustomer(clientResponse.getName());
        reportDTO.setAccountNumber(accountDTO.getAccountNumber());
        reportDTO.setType(accountDTO.getAccountType());
        reportDTO.setInitialBalance(accountDTO.getInitialBalance());
        reportDTO.setStatus(movementDTO.getStatus());
        reportDTO.setMovement(movementDTO.getAmount());
        reportDTO.setAvailableBalance(movementDTO.getBalance());
        return reportDTO;
    }

    private String castDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    private LocalDate convertStringToLocalDate(String dateStr){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid date format");
        }

    }
}
