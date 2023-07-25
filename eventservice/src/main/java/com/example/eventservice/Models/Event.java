package com.example.eventservice.Models;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer eventId;

    @Column(name = "capacity")
    private Integer eventCapacity;

    @Column(name = "description")
    private String eventDescription;

    @Column(name = "location_id")
    private Integer eventLocationId;

    @Column(name = "organizer_id")
    private Integer eventOrganizerId;

    @Column(name = "scheduleid")
    private Integer eventScheduleId;

    @Column(name = "tags")
    private String eventTags;

    @Column(name = "title")
    private String eventTitle;

    @Column(name = "type_id")
    private Integer eventTypeId;

    @Column(name = "eventdate")
    private Date eventDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "locationid", insertable = false, updatable = false)
    private Locations eventLocation;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "eventtypeid", insertable = false, updatable = false)
    private EventType eventType;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleid", referencedColumnName = "eventscheduleid", insertable = false, updatable = false)
    private EventSchedule eventSchedule;
    
    
    @Transient
    private String eventAction;

    // standard getters and setters...
    public String getEventAction() {
        return this.eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public Integer getEventScheduleId() {
        return this.eventScheduleId;
    }

    public void setEventScheduleId(int eventScheduleId) {
        this.eventScheduleId = eventScheduleId;
    }

    public Integer getEventTypeId() {
        return this.eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Integer getEventLocationId() {
        return this.eventLocationId;
    }

    public void setEventLocationId(int eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public int getEventOrganizerId() {
        return this.eventOrganizerId;
    }

    public void setEventOrganizerId(int eventOrganizerId) {
        this.eventOrganizerId = eventOrganizerId;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Integer getEventCapacity() {
        return eventCapacity;
    }

    public void setEventCapacity(Integer eventCapacity) {
        this.eventCapacity = eventCapacity;
    }

    public String getEventTags() {
        return this.eventTags;
    }

    public void setEventTags(String eventTags) {
        this.eventTags = eventTags;
    }

    public Integer getEventId() {
        return eventId;
    } 

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventDate() {
        return this.eventDate;
    }
}