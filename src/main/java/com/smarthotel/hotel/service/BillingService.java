package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.FoodOrder;
import com.smarthotel.hotel.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final OrderService orderService;

    public double calculateTotalBill(Customer customer) {
        // Calculate room cost
        Room room = customer.getRoom();
        LocalDateTime checkIn = customer.getCheckInDate();
        LocalDateTime checkOut = LocalDateTime.now();

        long days = Duration.between(checkIn, checkOut).toDays() + 1;
        double roomCost = days * room.getPricePerDay();

        // Calculate food cost
        double foodCost = orderService.findActiveOrdersByCustomer(customer.getId())
                .stream()
                .mapToDouble(order -> order.getFoodItem().getPrice() * order.getQuantity())
                .sum();

        return roomCost + foodCost;
    }
}
