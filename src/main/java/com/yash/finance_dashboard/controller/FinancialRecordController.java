package com.yash.finance_dashboard.controller;

import com.yash.finance_dashboard.model.FinancialRecord;
import com.yash.finance_dashboard.model.RecordType;
import com.yash.finance_dashboard.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    public FinancialRecordController(FinancialRecordService recordService) {
        this.recordService = recordService;
    }

    // Create Record
    @PostMapping
    public FinancialRecord createRecord(@Valid @RequestBody FinancialRecord record) {
        return recordService.createRecord(record);
    }

    // Get All Records
    @GetMapping
    public List<FinancialRecord> getAllRecords() {
        return recordService.getAllRecords();
    }

    // Filter by Type
    @GetMapping("/type/{type}")
    public List<FinancialRecord> getByType(@PathVariable RecordType type) {
        return recordService.getByType(type);
    }

    // Filter by Category
    @GetMapping("/category/{category}")
    public List<FinancialRecord> getByCategory(@PathVariable String category) {
        return recordService.getByCategory(category);
    }

    // Filter by Date Range
    @GetMapping("/date")
    public List<FinancialRecord> getByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return recordService.getByDateRange(start, end);
    }

    // Delete Record
    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

    //Update record
    @PutMapping("/{id}")
    public FinancialRecord updateRecord(@PathVariable Long id,
                                        @Valid @RequestBody FinancialRecord record) {
        return recordService.updateRecord(id, record);
    }
}