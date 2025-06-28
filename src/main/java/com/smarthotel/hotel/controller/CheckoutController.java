package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.FoodOrder;
import com.smarthotel.hotel.service.BillingService;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.OrderService;
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

    @GetMapping
    public String checkoutForm(Model model) {
        model.addAttribute("customers", customerService.findAllActiveCustomers());
        return "checkout/process";
    }

    @PostMapping
    public String processCheckout(@RequestParam Long customerId, Model model) {
        log.info("Processing checkout for customer ID: {}", customerId);

        try {
            Customer customer = customerService.findById(customerId).orElseThrow();
            log.info("Found customer: {}", customer.getName());

            LocalDateTime checkoutDate = LocalDateTime.now();

            // Calculate days stayed
            LocalDateTime checkIn = customer.getCheckInDate();
            long days = Duration.between(checkIn, checkoutDate).toDays();
            if (days == 0) days = 1; // At least one day

            // Calculate room cost
            double roomCost = days * customer.getRoom().getPricePerDay();

            // Get all food orders
            List<FoodOrder> orders = orderService.findAllOrdersByCustomer(customerId);

            // Calculate food total
            double foodTotal = orders.stream()
                    .mapToDouble(order -> order.getFoodItem().getPrice() * order.getQuantity())
                    .sum();

            // Calculate total bill
            double totalBill = roomCost + foodTotal;

            log.info("Days: {}, Room cost: {}, Food total: {}, Total bill: {}",
                    days, roomCost, foodTotal, totalBill);

            // Set checkout date and process checkout
            customer.setCheckOutDate(checkoutDate);
            customerService.checkoutCustomer(customerId);
            orderService.markOrdersAsBilled(customerId);

            // Pass all required data to template
            model.addAttribute("customer", customer);
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
