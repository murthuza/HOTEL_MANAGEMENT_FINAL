package com.smarthotel.hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Revenue {
    @Id
    private Long id = 1L; // Singleton entity
    private double totalRevenue;
}
