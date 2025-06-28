package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.OrderService;
import com.smarthotel.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final RoomService roomService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roomCount", roomService.findAllRooms().size());
        model.addAttribute("customerCount", customerService.findAllActiveCustomers().size());

        // Get pending orders count
        long pendingOrders = customerService.findAllActiveCustomers().stream()
                .mapToLong(customer -> orderService.findActiveOrdersByCustomer(customer.getId()).size())
                .sum();

        model.addAttribute("orderCount", pendingOrders);
        return "dashboard";
    }
}
