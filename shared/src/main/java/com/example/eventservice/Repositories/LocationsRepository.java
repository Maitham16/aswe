package com.example.eventservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventservice.Models.Locations;

public interface LocationsRepository extends JpaRepository<Locations, Integer> {

}
