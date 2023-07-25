package com.example.eventservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventservice.Models.EventSchedule;

public interface EventScheduleRepository extends JpaRepository<EventSchedule, Integer> {

}
