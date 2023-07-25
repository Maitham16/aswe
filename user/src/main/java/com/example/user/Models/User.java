package com.example.user.Models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String preferences;

    @ElementCollection
    private List<Long> bookmarkedEvents;

    @ElementCollection
    private List<Long> attendedEvents;

    // getters and setters for all fields
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;

    }

    public List<Long> getAttendedEvents() {
        return attendedEvents;
    }

    public void setAttendedEvents(List<Long> attendedEvents) {
        this.attendedEvents = attendedEvents;
    }

    public List<Long> getBookmarkedEvents() {
        return bookmarkedEvents;
    }

    public void setBookmarkedEvents(List<Long> bookmarkedEvents) {
        this.bookmarkedEvents = bookmarkedEvents;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

}
