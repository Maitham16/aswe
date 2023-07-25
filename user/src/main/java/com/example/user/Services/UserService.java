package com.example.user.Services;

import com.example.user.Models.User;
import com.example.user.Repositories.UserRepository;
import com.example.user.Services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword()); 
                    user.setRole(updatedUser.getRole());
                    user.setPreferences(updatedUser.getPreferences());
                    user.setBookmarkedEvents(updatedUser.getBookmarkedEvents());
                    user.setAttendedEvents(updatedUser.getAttendedEvents());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    updatedUser.setId(id);
                    updatedUser.setPassword(updatedUser.getPassword());
                    return userRepository.save(updatedUser);
                });
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    public String getUserPreferencesById(int id) {
        LOGGER.info("Fetching user preferences for user with ID: {}", id);

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String preferences = user.getPreferences();
            LOGGER.info("User preferences found");
            return preferences;
        }

        LOGGER.warn("User with ID {} not found. Returning empty preferences.", id);
        return "no preferences found";
    }

    public String getUserEmail(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}