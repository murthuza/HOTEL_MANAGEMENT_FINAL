package com.smarthotel.hotel.repository;

import com.smarthotel.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByAvailableTrue();
    boolean existsByRoomNumber(String roomNumber);
}
