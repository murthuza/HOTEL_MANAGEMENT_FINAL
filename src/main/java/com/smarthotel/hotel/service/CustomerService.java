package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.Customer;
import com.smarthotel.hotel.model.Room;
import com.smarthotel.hotel.repository.CustomerRepository;
import com.smarthotel.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAllActiveCustomers() {
        return customerRepository.findByCheckOutDateIsNull();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public void checkoutCustomer(Long id) {
        customerRepository.findById(id).ifPresent(customer -> {
            customer.setCheckOutDate(LocalDateTime.now());
            Room room = customer.getRoom();
            if (room != null) {
                room.setAvailable(true);
                roomRepository.save(room);
            }
            customerRepository.save(customer);
        });
    }
}
