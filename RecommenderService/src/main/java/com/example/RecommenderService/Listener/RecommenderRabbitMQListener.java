package com.example.RecommenderService.Listener;

import com.example.RecommenderService.Services.RecommendationService;
import com.example.eventservice.Models.Event;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RecommenderRabbitMQListener {

    private final RecommendationService recommendationService;

    public RecommenderRabbitMQListener(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @RabbitListener(queues = "EventManagementQueue")
    public void receiveMessage(Event event) {
        switch (event.getEventAction()) {
            case "CREATE":
                recommendationService.handleEventCreation(event);
                break;
            case "UPDATE":
                recommendationService.handleEventUpdate(event);
                break;
            case "DELETE":
                recommendationService.handleEventDeletion(event);
                break;
            default:
                break;
        }
    }
    
}