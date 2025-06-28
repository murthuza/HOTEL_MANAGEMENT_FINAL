package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.Room;
import com.smarthotel.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public String manageRooms(Model model) {
        model.addAttribute("rooms", roomService.findAllRooms());
        return "room/manage";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("room", new Room());
        return "room/form";
    }

    @PostMapping
    public String saveRoom(@ModelAttribute Room room, Model model) {
        if (roomService.roomNumberExists(room.getRoomNumber())) {
            model.addAttribute("error", "Room number already exists");
            return "room/form";
        }
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.findById(id).orElseThrow());
        return "room/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}
