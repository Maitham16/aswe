package com.example.eventservice.Controllers;

import com.example.eventservice.Models.Event;
import com.example.eventservice.Models.EventType;
import com.example.eventservice.Repositories.EventsRepository;
import com.example.eventservice.Services.RabbitMQPublisher;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events/ui")
public class EventControllerUI {

    private final RabbitMQPublisher publisher;
    private final EventsRepository eventsRepository;

    public EventControllerUI(RabbitMQPublisher rabbitMQPublisher,
                             EventsRepository eventsRepository) {
        this.publisher = rabbitMQPublisher;
        this.eventsRepository = eventsRepository;
    }

    @GetMapping("/create")
    public String showCreateEventForm() {
        return "create";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event, RedirectAttributes redirectAttributes) {
        try {
            publisher.createEvent(event);
            redirectAttributes.addFlashAttribute("message", "Event created successfully!");
            return "create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error creating event!");
            return "redirect:/events/ui/error";
        }
    }

    @GetMapping("/search")
    public String searchEventForm(Model model) {
        return "event-search";
    }

    @GetMapping("/{organizerId}")
    public String getEventsByOrganizer(@PathVariable("organizerId") Integer organizerId, Model model) {
        List<Event> events = eventsRepository.findByEventOrganizerId(organizerId);
        model.addAttribute("events", events);
        return "inventory";
    }    

    @GetMapping("/update/{id}")
    public String showUpdateEventForm(@PathVariable("id") Integer id, Model model) {
        Event event = eventsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        model.addAttribute("event", event);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable("id") Integer id, @Valid Event eventDetails,
            BindingResult result, RedirectAttributes redirectAttributes) {
            if (result.hasErrors()) {
            eventDetails.setEventId(id);
            return "update";
        }
    
        Event event = eventsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
    
        event.setEventCapacity(eventDetails.getEventCapacity());
        event.setEventDescription(eventDetails.getEventDescription());
        event.setEventLocationId(eventDetails.getEventLocationId());
        event.setEventOrganizerId(eventDetails.getEventOrganizerId());
        event.setEventScheduleId(eventDetails.getEventScheduleId());
        event.setEventTags(eventDetails.getEventTags());
        event.setEventTitle(eventDetails.getEventTitle());
        event.setEventTypeId(eventDetails.getEventTypeId());
        event.setEventDate(eventDetails.getEventDate());
    
        Event updatedEvent = eventsRepository.save(event);
    
        redirectAttributes.addFlashAttribute("message", "Event updated successfully!");
        return "redirect:/events/ui/" + updatedEvent.getEventOrganizerId();
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        publisher.deleteEvent(id);
        redirectAttributes.addFlashAttribute("message", "Event deleted successfully!");
        return "deleted";
    }

    @GetMapping("/inventory")
    public String eventInventory() {
        return "inventory";
    }

    @GetMapping("/created")
    public String eventCreated() {
        return "created";
    }


    @GetMapping("/updated")
    public String eventUpdated() {
        return "updated";
    }

    @GetMapping("/deleted")
    public String eventDeleted() {
        return "deleted";
    }

    @GetMapping("/allevents")
    @ResponseBody
    public List<Event> getEvents() {
        return publisher.getAllEvents();
    }

    @GetMapping("/eventTypes/{id}")
    @ResponseBody
    public EventType getEventType(@PathVariable("id") Integer id) {
        return publisher.getEventType(id);
    }

    @GetMapping("/eventDescription/{id}")
    @ResponseBody
    public String getEventDescription(@PathVariable("id") Integer id) {
        return publisher.getEventDescription(id);
    }

    @GetMapping("/eventTypeName/{id}")
    @ResponseBody
    public String getEventTypeName(@PathVariable("id") Integer id) {
        return publisher.getEventTypeName(id);
    }

}    