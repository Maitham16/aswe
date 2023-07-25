package com.example.eventservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventservice.Models.EventType;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {

}
