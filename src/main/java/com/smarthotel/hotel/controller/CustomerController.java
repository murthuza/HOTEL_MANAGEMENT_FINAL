package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.Room;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final RoomService roomService;

    @GetMapping("/checkin")
    public String showCheckinForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("availableRooms", roomService.findAvailableRooms());
        return "customer/checkin";
    }

    @PostMapping
    public String checkinCustomer(@ModelAttribute Customer customer,
                                  @RequestParam Long roomId) {
        Room room = roomService.findById(roomId).orElseThrow();
        room.setAvailable(false);
        roomService.saveRoom(room);

        customer.setRoom(room);
        customer.setCheckInDate(LocalDateTime.now());
        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }


    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAllActiveCustomers());
        return "customer/list";
    }

    @GetMapping("/{id}")
    public String customerDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id).orElseThrow();
        model.addAttribute("customer", customer);
        return "customer/details";
    }
}
