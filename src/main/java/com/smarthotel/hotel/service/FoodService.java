package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.FoodItem;
import com.smarthotel.hotel.repository.FoodItemRepository;
import com.smarthotel.hotel.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final FoodOrderRepository foodOrderRepository; // ✅ Make sure this is here!

    public List<FoodItem> findAllFoodItems() {
        return foodItemRepository.findByActiveTrue();
    }

    public FoodItem saveFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public void deleteFoodItem(Long id) {
        FoodItem item = foodItemRepository.findById(id).orElseThrow();
        item.setActive(false);
        foodItemRepository.save(item);
    }


    public FoodItem findById(Long id) {
        return foodItemRepository.findById(id).orElseThrow();
    }
}
