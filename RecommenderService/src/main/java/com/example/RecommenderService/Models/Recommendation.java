package com.example.RecommenderService.Models;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Recommendation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String recommendationId;
    private Integer userId;
    private Integer eventId;
    private String recommendationReason;
    private boolean notified;
    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // standard getters and setters
    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public boolean getNotified() {
        return notified;
    }
}