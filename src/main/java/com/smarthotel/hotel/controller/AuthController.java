package com.smarthotel.hotel.controller;

import com.smarthotel.hotel.model.User;
import com.smarthotel.hotel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    // Remove PasswordEncoder injection here
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Password matching validation
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "auth/register";
        }

        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "auth/register";
        }

        // Let UserService handle password encoding
        userService.registerUser(user);
        return "redirect:/login?registered";
    }
}
