package com.example.RecommenderService.Controllers;

import com.example.RecommenderService.Models.Recommendation;
import com.example.RecommenderService.Services.RecommendationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<Recommendation> getAllRecommendations() {
        return recommendationService.findAll();
    }

    @PostMapping
    public Recommendation createRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.save(recommendation);
    }

    @GetMapping("/{id}")
    public Recommendation getRecommendation(@PathVariable String id) {
        return recommendationService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRecommendation(@PathVariable String id) {
        recommendationService.delete(id);
    }

    @GetMapping("/generate/{userId}")
    public ResponseEntity<String> generateRecommendations(@PathVariable Integer userId) {
        recommendationService.generateRecommendationsForUser(userId);
        return new ResponseEntity<>("Recommendations generated successfully for User ID: " + userId, HttpStatus.OK);
    }
        
    @GetMapping("/user/{userId}")
    public List<Recommendation> getUserRecommendations(@PathVariable Integer userId) {
        return recommendationService.findByUserId(userId);
    }

    @GetMapping("/allusers")
    public ResponseEntity<String> generateRecommendationsForAll(@PathVariable Integer eventId) {
        recommendationService.generateRecommendationsForAllUsers(eventId);
        return new ResponseEntity<>("Recommendations generated successfully for User ID: " + eventId, HttpStatus.OK);
    }
}