package com.yash.finance_dashboard.service;

import com.yash.finance_dashboard.model.FinancialRecord;
import com.yash.finance_dashboard.model.RecordType;
import com.yash.finance_dashboard.model.User;
import com.yash.finance_dashboard.repository.FinancialRecordRepository;
import com.yash.finance_dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Month;

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

    //Update record
    public FinancialRecord updateRecord(Long id, FinancialRecord updatedRecord) {

        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        existing.setAmount(updatedRecord.getAmount());
        existing.setType(updatedRecord.getType());
        existing.setCategory(updatedRecord.getCategory());
        existing.setDate(updatedRecord.getDate());
        existing.setDescription(updatedRecord.getDescription());

        return recordRepository.save(existing);
    }

    public Map<String, Double> getSummary() {

        List<FinancialRecord> records = recordRepository.findAll();

        double totalIncome = records.stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = records.stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpense;

        Map<String, Double> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("netBalance", netBalance);

        return summary;
    }


    public Map<String, Double> getCategorySummary() {

        List<FinancialRecord> records = recordRepository.findAll();

        Map<String, Double> categoryMap = new HashMap<>();

        for (FinancialRecord record : records) {

            String category = record.getCategory();
            double amount = record.getAmount();

            categoryMap.put(
                    category,
                    categoryMap.getOrDefault(category, 0.0) + amount
            );
        }

        return categoryMap;
    }

    public Map<String, Double> getMonthlyTrends() {

        List<FinancialRecord> records = recordRepository.findAll();

        Map<String, Double> monthlyMap = new HashMap<>();

        for (FinancialRecord record : records) {

            String month = record.getDate().getMonth().toString(); // JANUARY, FEBRUARY
            double amount = record.getAmount();

            monthlyMap.put(
                    month,
                    monthlyMap.getOrDefault(month, 0.0) + amount
            );
        }

        return monthlyMap;
    }

    public List<FinancialRecord> searchByCategory(String category) {
        return recordRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<FinancialRecord> getRecentRecords() {
        return recordRepository.findTop5ByOrderByDateDesc();
    }
}