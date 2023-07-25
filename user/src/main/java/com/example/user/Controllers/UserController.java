package com.example.user.Controllers;

import com.example.user.Models.User;
import com.example.user.Repositories.UserRepository;
import com.example.user.Services.UserService;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/users/ui")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String getUsers(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user != null ? user : new User()); 
        model.addAttribute("users", userService.getUsers());
        return "users";
    }    

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model) {
        User createdUser = userService.createUser(user);
        model.addAttribute("user", createdUser);
        model.addAttribute("message", "User created successfully");
        return "redirect:/users/ui";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user.getId(), user);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        return "redirect:/users/ui";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserForm(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.deleteUser(user.getId());
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        return "redirect:/users/ui";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "details";
    }

    @GetMapping("/details/{id}")
    public String formUserDetails(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "details";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        if (userService.authenticateUser(email, password)) {
            User user = userService.getUserByEmail(email);
            session.setAttribute("loggedInUser", user); // store the user in the session
            if (user.getRole().equalsIgnoreCase("organizer")) {
                return "redirect:/users/ui";
            } else {
                return "redirect:/users/ui"; 
            }
        } else {
            model.addAttribute("loginError", "Email or password is incorrect. Please try again.");
            return "login"; // back to the login page
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/users/ui/login"; // corrected path
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }  
    
    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> events = userRepository.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }  

    @GetMapping("/{id}/preferences")
    public ResponseEntity<String> getUserPreferences(@PathVariable Integer id) {
        String preferences = userService.getUserPreferencesById(id);
        return ResponseEntity.ok(preferences);
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getUserEmail(@PathVariable Integer id) {
        String email = userService.getUserEmail(id);
        if (email != null) {
            return ResponseEntity.ok(email);
        }
        return ResponseEntity.notFound().build();
    }
}
