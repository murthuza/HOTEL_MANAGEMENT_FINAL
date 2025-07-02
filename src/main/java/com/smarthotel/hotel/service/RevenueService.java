//package com.smarthotel.hotel.service;
//
//import com.smarthotel.hotel.model.RevenueRecord;
//import com.smarthotel.hotel.repository.RevenueRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RevenueService {
//    private final RevenueRepository revenueRepository;
//
//    public RevenueService(RevenueRepository revenueRepository) {
//        this.revenueRepository = revenueRepository;
//    }
//
//    public void addRevenue(double amount, String description) {
//        RevenueRecord record = new RevenueRecord(amount, description);
//        revenueRepository.save(record);
//    }
//
//    public double getTotalRevenue() {
//        return revenueRepository.findAll().stream()
//                .mapToDouble(RevenueRecord::getAmount)
//                .sum();
//    }
//
//    public List<RevenueRecord> getAllRevenueRecords() {
//        return revenueRepository.findAll();
//    }
//}
