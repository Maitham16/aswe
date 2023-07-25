package com.example.eventservice.Models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table (name="eventschedule")
public class EventSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventscheduleid;
    private Timestamp starttime;
    private Timestamp endtime;
    private String description;

    public int getEventscheduleid() {
        return eventscheduleid;
    }

}
