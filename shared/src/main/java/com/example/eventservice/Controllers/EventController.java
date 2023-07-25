package com.example.eventservice.Controllers;

import com.example.eventservice.Exceptions.EventNotFoundException;
import com.example.eventservice.Models.Event;
import com.example.eventservice.Models.EventSchedule;
import com.example.eventservice.Models.EventType;
import com.example.eventservice.Models.Locations;
import com.example.eventservice.Repositories.EventScheduleRepository;
import com.example.eventservice.Repositories.EventTypeRepository;
import com.example.eventservice.Repositories.EventsRepository;
import com.example.eventservice.Repositories.LocationsRepository;
import com.example.eventservice.Services.RabbitMQPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventsRepository eventsRepository;
    private final RabbitMQPublisher publisher;

    public EventController(RabbitMQPublisher publisher, EventsRepository eventsRepository) {
        this.publisher = publisher;
        this.eventsRepository = eventsRepository;
    }

    @GetMapping("/event")
    public ResponseEntity<Event> getEvent(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id == null) {
            throw new EventNotFoundException("Event ID cannot be null.");
        }
        Optional<Event> eventOptional = eventsRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            throw new EventNotFoundException("No event with ID " + id + " exists.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventsRepository.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        rabbitMQPublisher.createEvent(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    // Inject repositories
    @Autowired
    private EventScheduleRepository eventScheduleRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private LocationsRepository locationsRepository;

    @GetMapping("/eventschedule")
    @ResponseBody
    public List<EventSchedule> getEventSchedule() {
        return eventScheduleRepository.findAll();
    }

    @GetMapping("/eventtype")
    @ResponseBody
    public List<EventType> getEventType() {
        return eventTypeRepository.findAll();
    }

    @GetMapping("/locations")
    @ResponseBody
    public List<Locations> getLocations() {
        return locationsRepository.findAll();
    }

    @PostMapping("/event")
    public ResponseEntity<String> postEvent(@RequestBody Event event) {
        publisher.sendEventCreation(event);
        return ResponseEntity.ok().body("{\"message\": \"Event creation details sent to RabbitMQ successfully!\"}");
    }
}
