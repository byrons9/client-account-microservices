package com.test.account.controller;

import com.test.account.model.ReportDTO;
import com.test.account.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private  final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<ReportDTO> getMovementsByDateClientName(
            @RequestParam String name,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return reportService.getMovementsByDateClientName(startDate, endDate, name);
    }
}
