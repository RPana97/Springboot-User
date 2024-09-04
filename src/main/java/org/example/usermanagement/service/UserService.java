package org.example.usermanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.usermanagement.entity.User;
import org.example.usermanagement.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User registerUser(User user) {
        // Log user registration
        LOGGER.info("Registering new user: " + user.getUsername());

        // In a real application, you should hash the password before saving
        return userRepository.save(user);
    }

    // Retrieve user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    user.setRole(updatedUser.getRole());
                    LOGGER.info("Updated user details for: " + user.getUsername());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        LOGGER.info("Deleting user with ID: " + id);
        userRepository.deleteById(id);
    }
}
