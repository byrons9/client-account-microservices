package com.test.account.service;

import com.test.account.model.ReportDTO;

import java.util.List;

public interface IReportService {
    List<ReportDTO> getMovementsByDateClientName(String startDateStr, String endDateStr, String customerName);
}
