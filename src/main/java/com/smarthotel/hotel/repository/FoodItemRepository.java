package com.smarthotel.hotel.repository;

import com.smarthotel.hotel.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByActiveTrue();

}
