package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.FoodOrder;
import com.smarthotel.hotel.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final FoodOrderRepository orderRepository;

    public FoodOrder saveOrder(FoodOrder order) {
        return orderRepository.save(order);
    }

    public List<FoodOrder> findActiveOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerIdAndBilledFalse(customerId);
    }

    public List<FoodOrder> findAllOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public void markOrdersAsBilled(Long customerId) {
        List<FoodOrder> orders = orderRepository.findByCustomerIdAndBilledFalse(customerId);
        orders.forEach(order -> order.setBilled(true));
        orderRepository.saveAll(orders);
    }
}
