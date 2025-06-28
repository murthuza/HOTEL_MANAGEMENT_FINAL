package com.smarthotel.hotel.service;

import com.smarthotel.hotel.model.Room;
import com.smarthotel.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> findAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public boolean roomNumberExists(String roomNumber) {
        return roomRepository.existsByRoomNumber(roomNumber);
    }
}
