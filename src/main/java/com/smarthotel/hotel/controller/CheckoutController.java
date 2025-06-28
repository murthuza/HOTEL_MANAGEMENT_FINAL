package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.service.BillingService;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final CustomerService customerService;
    private final BillingService billingService;
    private final OrderService orderService;

    @GetMapping
    public String checkoutForm(Model model) {
        model.addAttribute("customers", customerService.findAllActiveCustomers());
        return "checkout/process";
    }

    @PostMapping("/{customerId}")
    public String processCheckout(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId).orElseThrow();
        double totalBill = billingService.calculateTotalBill(customer);

        // Process checkout
        customerService.checkoutCustomer(customerId);
        orderService.markOrdersAsBilled(customerId);

        model.addAttribute("customer", customer);
        model.addAttribute("bill", totalBill);
        model.addAttribute("orders", orderService.findAllOrdersByCustomer(customerId));
        return "bill/view";
    }
}
