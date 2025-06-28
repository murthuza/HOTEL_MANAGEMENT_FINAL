package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.service.BillingService;
import com.smarthotel.hotel.service.CustomerService;
import com.smarthotel.hotel.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Add this import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j // Add this annotation
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
        log.info("Processing checkout for customer ID: {}", customerId); // Use 'log' not 'logger'

        try {
            Customer customer = customerService.findById(customerId).orElseThrow();
            log.info("Found customer: {}", customer.getName());

            double totalBill = billingService.calculateTotalBill(customer);
            log.info("Calculated bill: {}", totalBill);

            customerService.checkoutCustomer(customerId);
            orderService.markOrdersAsBilled(customerId);

            model.addAttribute("customer", customer);
            model.addAttribute("bill", totalBill);
            model.addAttribute("orders", orderService.findAllOrdersByCustomer(customerId));
            return "bill/view";
        } catch (Exception e) {
            log.error("Error processing checkout for customer {}: {}", customerId, e.getMessage(), e);
            throw e;
        }
    }
}
