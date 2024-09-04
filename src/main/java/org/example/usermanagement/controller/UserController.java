package org.example.usermanagement.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.example.usermanagement.entity.User;
import org.example.usermanagement.service.UserService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Register a new user.
     * Accessible to everyone.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // By default, assign role USER to newly registered users
        user.setRole("USER");
        User registeredUser = userService.registerUser(user);
        System.out.println("User registered: " + user.getUsername()); // Log registration
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Get details of the authenticated user.
     * Accessible to authenticated users with role USER or ADMIN.
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        String username = principal.getName();
        User user = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));

        System.out.println("User logged in: " + username); // Log login action
        return ResponseEntity.ok(user);
    }

    /**
     * Update details of the authenticated user.
     * Accessible to authenticated users with role USER or ADMIN.
     */
    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCurrentUser(Principal principal, @Valid @RequestBody User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        String username = principal.getName();
        User existingUser = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found"));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        // Prevent changing role via this endpoint
        User savedUser = userService.updateUser(existingUser.getId(), existingUser);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Get details of a user by ID.
     * Accessible to users with role ADMIN or the user themselves.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<User> getUserById(@PathVariable Long id, Principal principal) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("User not found with ID: " + id));
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users.
     * Accessible only to users with role ADMIN.
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Delete a user by ID.
     * Accessible only to users with role ADMIN.
     */
    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
