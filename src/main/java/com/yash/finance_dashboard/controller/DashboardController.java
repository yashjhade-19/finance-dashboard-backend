package com.yash.finance_dashboard.controller;

import com.yash.finance_dashboard.model.FinancialRecord;
import com.yash.finance_dashboard.service.FinancialRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final FinancialRecordService recordService;

    public DashboardController(FinancialRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/summary")
    public Map<String, Double> getSummary() {
        return recordService.getSummary();
    }

    @GetMapping("/category")
    public Map<String, Double> getCategorySummary() {
        return recordService.getCategorySummary();
    }

    @GetMapping("/monthly")
    public Map<String, Double> getMonthlyTrends() {
        return recordService.getMonthlyTrends();
    }

    @GetMapping("/recent")
    public List<FinancialRecord> getRecentRecords() {
        return recordService.getRecentRecords();
    }
}