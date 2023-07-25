package com.example.eventservice.Listener;

import com.example.eventservice.Models.Event;
import com.example.eventservice.Repositories.EventsRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    private final EventsRepository repository;

    public RabbitMQListener(EventsRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "EventManagementQueue")
    public void consume(Event event) {
        if (event.getEventCapacity() != null) {
            repository.save(event);
        } else {
            repository.delete(event);
        }
    }
}
