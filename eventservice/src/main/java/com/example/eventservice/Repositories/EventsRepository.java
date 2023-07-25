package com.example.eventservice.Repositories;

import com.example.eventservice.Models.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Event, Integer> {
    List<Event> findByEventOrganizerId(Integer eventOrganizerId);
    
    default List<Event> findByEventOrganizerId(String eventOrganizerId) {
        Integer organizerId = Integer.parseInt(eventOrganizerId);
        return findByEventOrganizerId(organizerId);
    }

    Event getEventByEventId(Long eventId);
}
