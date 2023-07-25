package com.example.RecommenderService.Repositories;

import com.example.RecommenderService.Models.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    List<Recommendation> findByEventId(Integer eventId);

    List<Recommendation> findByUserId(Integer userId);

    Optional<Recommendation> findById(String id);

    void deleteById(String id);
}