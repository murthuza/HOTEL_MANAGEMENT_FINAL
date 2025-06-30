package com.smarthotel.hotel;

import com.smarthotel.hotel.model.Revenue;
import com.smarthotel.hotel.repository.RevenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;
@SpringBootApplication
@Slf4j
public class SmartHotelProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHotelProjectApplication.class, args);
	}
	@Bean
	CommandLineRunner initRevenue(RevenueRepository revenueRepo) {
		return args -> {
			if (!revenueRepo.existsById(1L)) {
				Revenue revenue = new Revenue();
				revenue.setId(1L);
				revenue.setTotalRevenue(0.0);
				revenueRepo.save(revenue);
				System.out.println("Initialized revenue record with ₹0");
			}
		};
	}

}
