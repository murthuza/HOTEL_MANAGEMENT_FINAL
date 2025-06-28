package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.FoodItem;
import com.smarthotel.hotel.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodItemRepository foodItemRepository;

    public FoodItem saveFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> findAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }
}
