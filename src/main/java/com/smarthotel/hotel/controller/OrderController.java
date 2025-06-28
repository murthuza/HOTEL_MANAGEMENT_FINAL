package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.FoodOrder;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.FoodService;
import com.smarthotel.hotel.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final FoodService foodService;

    @GetMapping("/create/{customerId}")
    public String showOrderForm(@PathVariable Long customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId).orElseThrow());
        model.addAttribute("foodItems", foodService.findAllFoodItems());
        model.addAttribute("order", new FoodOrder());
        return "order/create";
    }

    @PostMapping("/{customerId}")
    public String saveOrder(@PathVariable Long customerId,
                            @ModelAttribute FoodOrder order,
                            @RequestParam Long foodItemId) {
        Customer customer = customerService.findById(customerId).orElseThrow();
        order.setCustomer(customer);
        order.setFoodItem(foodService.findAllFoodItems().stream()
                .filter(f -> f.getId().equals(foodItemId))
                .findFirst()
                .orElseThrow());

        orderService.saveOrder(order);
        return "redirect:/orders/list/" + customerId;
    }

    @GetMapping("/list/{customerId}")
    public String listOrders(@PathVariable Long customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId).orElseThrow());
        model.addAttribute("orders", orderService.findActiveOrdersByCustomer(customerId));
        return "order/list";
    }
}
