package com.example.eventservice.Models;

import java.sql.Date;

public class EventDto {
    private Integer eventId;
    private Integer eventCapacity;
    private String eventDescription;
    private Integer eventLocationId;
    private Integer eventOrganizerId;
    private Integer eventScheduleId;
    private String eventTags;
    private String eventTitle;
    private Integer eventTypeId;
    private Date eventDate;

    public Integer getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Integer getEventCapacity() {
        return this.eventCapacity;
    }

    public void setEventCapacity(Integer eventCapacity) {
        this.eventCapacity = eventCapacity;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Integer getEventLocationId() {
        return this.eventLocationId;
    }

    public void setEventLocationId(int eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public Integer getEventOrganizerId() {
        return this.eventOrganizerId;
    }   

    public void setEventOrganizerId(int eventOrganizerId) {
        this.eventOrganizerId = eventOrganizerId;
    }

    public Integer getEventScheduleId() {
        return this.eventScheduleId;
    }

    public void setEventScheduleId(int eventScheduleId) {
        this.eventScheduleId = eventScheduleId;
    }

    public String getEventTags() {
        return this.eventTags;
    }

    public void setEventTags(String eventTags) {
        this.eventTags = eventTags;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Integer getEventTypeId() {
        return this.eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
