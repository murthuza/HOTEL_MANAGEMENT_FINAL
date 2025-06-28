package com.smarthotel.hotel.repository;

import com.smarthotel.hotel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCheckOutDateIsNull();
    List<Customer> findByRoomId(Long roomId);
}
