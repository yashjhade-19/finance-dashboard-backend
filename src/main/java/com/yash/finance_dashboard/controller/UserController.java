package com.yash.finance_dashboard.controller;

import com.yash.finance_dashboard.dto.UserResponseDTO;
import com.yash.finance_dashboard.model.User;
import com.yash.finance_dashboard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // Get All Users
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get User by ID
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Update User
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id,
                                      @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}