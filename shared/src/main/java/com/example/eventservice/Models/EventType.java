package com.example.eventservice.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table (name="eventtype")
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventtypeid;
    private String typename;

    public Integer getEventtypeid() {
        return eventtypeid;
    }

    public String getTypename() {
        return typename;
    }
}

