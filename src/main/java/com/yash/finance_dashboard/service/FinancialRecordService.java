package com.yash.finance_dashboard.service;

import com.yash.finance_dashboard.model.FinancialRecord;
import com.yash.finance_dashboard.model.RecordType;
import com.yash.finance_dashboard.model.User;
import com.yash.finance_dashboard.repository.FinancialRecordRepository;
import com.yash.finance_dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository recordRepository,
                                  UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    // Create record
    public FinancialRecord createRecord(FinancialRecord record) {

        Long userId = record.getCreatedBy().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        record.setCreatedBy(user);

        return recordRepository.save(record);
    }

    // Get all records
    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    // Filter by type
    public List<FinancialRecord> getByType(RecordType type) {
        return recordRepository.findByType(type);
    }

    // Filter by category
    public List<FinancialRecord> getByCategory(String category) {
        return recordRepository.findByCategory(category);
    }

    // Filter by date range
    public List<FinancialRecord> getByDateRange(LocalDate start, LocalDate end) {
        return recordRepository.findByDateBetween(start, end);
    }

    // Delete record
    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}