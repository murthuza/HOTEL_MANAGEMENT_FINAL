package com.smarthotel.hotel.repository;

import com.smarthotel.hotel.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByCustomerIdAndBilledFalse(Long customerId);
    List<FoodOrder> findByCustomerId(Long customerId);
}
