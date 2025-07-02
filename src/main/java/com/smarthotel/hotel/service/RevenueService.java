<<<<<<< HEAD
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
=======
package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.Revenue;
import com.smarthotel.hotel.repository.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RevenueService {
    private final RevenueRepository revenueRepository;

    @Transactional
    public void addToRevenue(double amount) {
        Revenue revenue = revenueRepository.findById(1L)
                .orElseGet(() -> {
                    Revenue newRevenue = new Revenue();
                    newRevenue.setId(1L);
                    newRevenue.setTotalRevenue(0.0);
                    return revenueRepository.save(newRevenue);
                });

        revenue.setTotalRevenue(revenue.getTotalRevenue() + amount);
        revenueRepository.save(revenue);
        System.out.println("Added ₹" + amount + " to revenue. New total: ₹" + revenue.getTotalRevenue());
    }

    public double getTotalRevenue() {
        return revenueRepository.findById(1L)
                .map(Revenue::getTotalRevenue)
                .orElse(0.0);
    }
}
>>>>>>> d0aa94cb0910a49c2ae8f6ab0fdc6fa0984c5f65
