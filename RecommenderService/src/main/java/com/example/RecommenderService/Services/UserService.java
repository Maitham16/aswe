package com.example.RecommenderService.Services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.user.Models.User;

@FeignClient(name = "user", url = "http://localhost:8072/api/users/ui")
public interface UserService {

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Integer id);

    @GetMapping("/allusers")
    List<User> getAllUsers();

    @GetMapping("/{id}/preferences")
    String getUserPreferences(@PathVariable Integer id);
}