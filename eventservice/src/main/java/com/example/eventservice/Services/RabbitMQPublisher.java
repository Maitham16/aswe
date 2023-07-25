package com.example.eventservice.Services;

import com.example.eventservice.Config.RabbitMQConfig;
import com.example.eventservice.Exceptions.RabbitMQException;
import com.example.eventservice.Exceptions.ResourceNotFoundException;
import com.example.eventservice.Models.Event;
import com.example.eventservice.Models.EventDto;
import com.example.eventservice.Models.EventSchedule;
import com.example.eventservice.Models.EventType;
import com.example.eventservice.Models.Locations;
import com.example.eventservice.Repositories.EventScheduleRepository;
import com.example.eventservice.Repositories.EventTypeRepository;
import com.example.eventservice.Repositories.EventsRepository;
import com.example.eventservice.Repositories.LocationsRepository;

import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventTypeRepository eventTypeRepository;
    private final LocationsRepository locationsRepository;
    private final EventsRepository eventsRepository;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate, EventScheduleRepository eventScheduleRepository,
            EventTypeRepository eventTypeRepository, LocationsRepository locationsRepository,
            EventsRepository eventsRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventScheduleRepository = eventScheduleRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.locationsRepository = locationsRepository;
        this.eventsRepository = eventsRepository;
    }

    public void sendEventCreation(Event event) {
        try {
            event.setEventAction("CREATE");
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", event);
        } catch (AmqpException e) {
            throw new RabbitMQException("Failed to send event creation message", e);
        }
    }

    public void sendEventUpdate(Event event) {
        event.setEventAction("UPDATE");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", event);
    }

    public void sendEventDeletion(Integer id) {
        Event event = new Event();
        event.setEventId(id);
        event.setEventAction("DELETE");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", event);
    }

    public void setScheduleId(Event event, int scheduleId) {
        EventSchedule eventSchedule = eventScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("EventSchedule", "id", scheduleId));
        event.setEventSchedule(eventSchedule);
    }

    public void setTypeId(Event event, int typeId) {
        EventType eventType = eventTypeRepository.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("EventType", "id", typeId));
        event.setEventType(eventType);
    }

    public void setLocationsId(Event event, int locationId) {
        Locations locations = locationsRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Locations", "id", locationId));
        event.setEventLocation(locations);
    }

    public void createEvent(Event event) {
        try {
            event.setEventAction("CREATE");
            Event savedEvent = eventsRepository.save(event);
            // Publish the event to RabbitMQ exchange
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", savedEvent);
        } catch (AmqpException e) {
            throw new RabbitMQException("Failed to send event creation message", e);
        }
    }

    public Event getEvent(Integer id) {
        return eventsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    }

    public Event getEventById(Integer id) {
        return eventsRepository.findById(id).orElse(null);
    }

    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setEventId(event.getEventId());
        dto.setEventCapacity(event.getEventCapacity());
        dto.setEventDescription(event.getEventDescription());
        dto.setEventId(event.getEventId());
        dto.setEventTitle(event.getEventTitle());
        dto.setEventLocationId(event.getEventLocationId());
        dto.setEventOrganizerId(event.getEventOrganizerId());
        dto.setEventScheduleId(event.getEventScheduleId());
        dto.setEventTypeId(event.getEventTypeId());
        dto.setEventDate(event.getEventDate());
        return dto;
    }

    public void updateEvent(Event event) {
        try {
            event.setEventAction("UPDATE");
            eventsRepository.save(event);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", event);
        } catch (AmqpException e) {
            throw new RabbitMQException("Failed to send event update message", e);
        }
    }

    public void deleteEvent(Integer id) {
        try {
            Event event = new Event();
            event.setEventId(id);
            event.setEventAction("DELETE");
            eventsRepository.deleteById(id);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "event_key", id);
        } catch (AmqpException e) {
            throw new RabbitMQException("Failed to send event deletion message", e);
        }
    }

    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }

    public EventType getEventType(Integer id) {
        return eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventType", "id", id));
    }

    public String getEventDescription(Integer id) {
        return eventsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id)).getEventDescription();
    }

    // get event type name by id
    public String getEventTypeName(Integer id) {
        return eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventType", "id", id)).getTypename();
    }

    // insert data to event type table
    public void createEventType(EventType eventType) {
        eventTypeRepository.save(eventType);
    }

    // insert data to event schedule table
    public void createEventSchedule(EventSchedule eventSchedule) {
        eventScheduleRepository.save(eventSchedule);
    }

    // insert data to locations table
    public void createLocations(Locations locations) {
        locationsRepository.save(locations);
    }
}
