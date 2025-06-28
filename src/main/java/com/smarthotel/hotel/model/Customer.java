package com.smarthotel.hotel.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String contactInfo;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
