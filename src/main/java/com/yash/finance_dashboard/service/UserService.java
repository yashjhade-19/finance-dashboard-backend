package com.yash.finance_dashboard.service;

import com.yash.finance_dashboard.dto.UserResponseDTO;
import com.yash.finance_dashboard.model.User;
import com.yash.finance_dashboard.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔁 Mapper (Entity → DTO)
    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setActive(user.isActive());
        return dto;
    }

    // ✅ Create user (encrypt password)
    public UserResponseDTO createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    // ✅ Get all users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ Get user by ID
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    // ✅ Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ✅ Update user (encrypt password only if changed)
    public UserResponseDTO updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        // 🔐 Encrypt password only if provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        existingUser.setRole(updatedUser.getRole());
        existingUser.setActive(updatedUser.isActive());

        User saved = userRepository.save(existingUser);
        return mapToDTO(saved);
    }
}