package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.FoodOrder;
import com.smarthotel.hotel.model.Room;
import com.smarthotel.hotel.service.BillingService;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.OrderService;
import com.smarthotel.hotel.service.RevenueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {
    private final CustomerService customerService;
    private final BillingService billingService;
    private final OrderService orderService;
    private final RevenueService revenueService;

    @GetMapping
    public String checkoutForm(Model model) {
        model.addAttribute("customers", customerService.findAllActiveCustomers());
        return "checkout/process";
    }

    @PostMapping
    @Transactional
    public String processCheckout(@RequestParam Long customerId, Model model) {
        log.info("Processing checkout for customer ID: {}", customerId);

        try {
            Customer customer = customerService.findById(customerId).orElseThrow();
            log.info("Found customer: {}", customer.getName());

            LocalDateTime checkoutDate = LocalDateTime.now();
            Room room = customer.getRoom(); // Store room reference BEFORE checkout

            // Calculate days stayed
            LocalDateTime checkIn = customer.getCheckInDate();
            long days = Duration.between(checkIn, checkoutDate).toDays();
            if (days == 0) days = 1;

            // Calculate room cost
            double roomCost = days * room.getPricePerDay();

            // Get all food orders
            List<FoodOrder> orders = orderService.findAllOrdersByCustomer(customerId);

            // Calculate food total
            double foodTotal = orders.stream()
                    .mapToDouble(order -> order.getFoodItem().getPrice() * order.getQuantity())
                    .sum();

            // Calculate total bill
            double totalBill = roomCost + foodTotal;
            revenueService.addToRevenue(totalBill);

            // Process checkout (sets room to null)
            customerService.checkoutCustomer(customerId);
            orderService.markOrdersAsBilled(customerId);

            // Pass room details explicitly
            model.addAttribute("customer", customer);
            model.addAttribute("room", room); // Pass room separately
            model.addAttribute("checkoutDate", checkoutDate);
            model.addAttribute("days", days);
            model.addAttribute("roomCost", roomCost);
            model.addAttribute("foodTotal", foodTotal);
            model.addAttribute("orders", orders);
            model.addAttribute("bill", totalBill);

            return "bill/view";
        } catch (Exception e) {
            log.error("Error processing checkout for customer {}: {}", customerId, e.getMessage(), e);
            throw e;
        }
    }
}
