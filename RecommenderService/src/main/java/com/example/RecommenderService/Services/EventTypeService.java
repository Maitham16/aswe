package com.example.RecommenderService.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.eventservice.Models.EventType;

@Service
public class EventTypeService {

    public EventType getEventType(Integer typeId) {
        String url = "http://localhost:8071/api/events/ui/eventTypes/" + typeId;
        
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, EventType.class);
    }

    public String eventTypeName(Integer typeId) {
        EventType eventType = getEventType(typeId);
        return eventType.getTypename();
    }
}