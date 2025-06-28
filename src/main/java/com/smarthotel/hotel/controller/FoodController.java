package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.FoodItem;
import com.smarthotel.hotel.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public String manageFood(Model model) {
        model.addAttribute("foodItems", foodService.findAllFoodItems());
        return "food/manage";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("foodItem", new FoodItem());
        return "food/form";
    }

    @PostMapping
    public String saveFoodItem(@ModelAttribute FoodItem foodItem) {
        foodService.saveFoodItem(foodItem);
        return "redirect:/food";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoodItem(@PathVariable Long id) {
        foodService.deleteFoodItem(id);
        return "redirect:/food";
    }
}
