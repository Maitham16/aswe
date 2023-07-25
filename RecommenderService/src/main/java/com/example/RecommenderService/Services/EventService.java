package com.example.RecommenderService.Services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.eventservice.Models.Event;

@FeignClient(name = "eventsservice", url = "http://localhost:8071/api/events/ui")
public interface EventService {

    @GetMapping("/allevents")
    List<Event> getAllEvents();

    @GetMapping("/eventDescription/{id}")
    String getEventDescription(@PathVariable("id") Integer id);

    // get event type name by id
    @GetMapping("/eventTypes/{id}")
    String getEventTypeName(@PathVariable Integer id);
}
