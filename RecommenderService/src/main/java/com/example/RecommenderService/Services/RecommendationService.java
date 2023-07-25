package com.example.RecommenderService.Services;

import com.example.RecommenderService.Config.RabbitMQConfig;
import com.example.RecommenderService.Models.Recommendation;
import com.example.RecommenderService.Repositories.RecommendationRepository;
import com.example.eventservice.Models.Event;
import com.example.user.Models.User;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class RecommendationService {
    // create reference variables for the repositories and services
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTypeService eventTypeService;

    // create a constructor for the class
    public RecommendationService(
            RecommendationRepository recommendationRepository,
            UserService userService,
            EventService eventService,
            EventTypeService eventTypeService) {
        this.recommendationRepository = recommendationRepository;
        this.userService = userService;
        this.eventService = eventService;
        this.eventTypeService = eventTypeService;
    }

    // create a method to get all recommendations
    public List<Recommendation> findAll() {
        return recommendationRepository.findAll();
    }

    // Logger to check the logs
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);

    // create a method to save a recommendation
    public Recommendation save(Recommendation recommendation) {
        LOGGER.info("Saving recommendation: {}", recommendation);
        return recommendationRepository.save(recommendation);
    }

    // create a method to find a recommendation by id
    public Recommendation findOne(String id) {
        return recommendationRepository.findById(id).orElse(null);
    }

    // create a method to delete a recommendation by id
    public void delete(String id) {
        recommendationRepository.deleteById(id);
    }

    // rabbitmq listener to receive messages from the event service
    @RabbitListener(queues = "EventManagementQueue")
    public void receiveMessage(Event event) {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(UUID.randomUUID().toString());
        recommendation.setEventId(event.getEventId());
        recommendationRepository.save(recommendation);
    }

    // create a method to handle create recommendations
    public void handleEventCreation(Event event) {
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            generateRecommendationsForUser(user.getId());
        }
    }

    // create a method to handle update recommendations
    public void handleEventUpdate(Event event) {
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            generateRecommendationsForUser(user.getId());
        }
    }

    // create a method to handle delete recommendations
    public void handleEventDeletion(Event event) {
        List<Recommendation> recommendations = recommendationRepository.findByEventId(event.getEventId());
        for (Recommendation recommendation : recommendations) {
            recommendationRepository.delete(recommendation);
        }
    }

    // create a method to generate recommendations for users with preferences.
    public void generateRecommendationsForUser(Integer userId) {
        LOGGER.info("Generating recommendations for user: {}", userId);

        Optional<String> optionalUserPreferences = Optional.ofNullable(userService.getUserPreferences(userId));

        String userPreferences = optionalUserPreferences.orElse("no Preferences");

        LOGGER.info("User {} preferences: {}", userId, userPreferences);

        // Find new events that match the user's preferences
        List<Event> allEvents = eventService.getAllEvents();

        LOGGER.info("All events: {}", allEvents);

        List<Event> matchingEvents = allEvents.stream()
                .filter(e -> {
                    String eventType = eventTypeService.eventTypeName(e.getEventTypeId());
                    return eventType != null && userPreferences.contains(eventType);
                }) // Only recommend events that match the user's preferences
                .filter(e -> e.getEventCapacity() > 0) // Only recommend events that have capacity left
                .sorted(Comparator.comparing(Event::getEventDate)) // Sort by date to recommend sooner events first
                .collect(Collectors.toList());

        LOGGER.info("Matching events for user {}: {}", userId, matchingEvents);

        // Generate recommendations based on these matching events with recommendationId
        for (Event event : matchingEvents) {
            Recommendation recommendation = new Recommendation();

            // Generate unique recommendationId
            String recommendationId = UUID.randomUUID().toString();
            recommendation.setRecommendationId(recommendationId);

            recommendation.setUserId(userId);
            recommendation.setEventId(event.getEventId());

            String reason = String.format(
                    "This event, titled '%s' and happening on %s, matches your preference for '%s' events. " +
                            "It has %d seats left. Here's what it's about: '%s'",
                    event.getEventTitle(), event.getEventDate(), eventService.getEventTypeName(event.getEventTypeId()),
                    event.getEventCapacity(),
                    eventService.getEventDescription(event.getEventId()));
            recommendation.setRecommendationReason(reason);
            recommendation.setNotified(false);
            recommendation.setMessage("Recommendations generated successfully for User ID: " + userId);

            // Save the recommendation
            Recommendation savedRecommendation = recommendationRepository.save(recommendation);
            LOGGER.info("Saved recommendation: {}", savedRecommendation);
        }
    }

    // create a method to get all recommendations for a user
    public List<Recommendation> findByUserId(Integer userId) {
        return recommendationRepository.findByUserId(userId);
    }

    // listener to receive messages from the event microservice
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveEvent(Event event) {
        // Generate recommendations based on the event
        generateRecommendationsForAllUsers(event.getEventId());
    }

    // function to generate recommendations for all users based on an event
    public void generateRecommendationsForAllUsers(Integer eventId) {

        LOGGER.info("Generating recommendations for all users based on event: {}", eventId);

        // Find new events that match the user's preferences
        List<Event> allEvents = eventService.getAllEvents();

        LOGGER.info("All events: {}", allEvents);

        List<Event> matchingEvents = allEvents.stream()
                .filter(e -> e.getEventId().equals(eventId)) // Only recommend events that match the received eventId
                .filter(e -> e.getEventCapacity() > 0) // Only recommend events that have capacity left
                .sorted(Comparator.comparing(Event::getEventDate)) // Sort by date to recommend sooner events first
                .collect(Collectors.toList());

        LOGGER.info("Matching events for event {}: {}", eventId, matchingEvents);

        // Get all users
        List<User> allUsers = userService.getAllUsers();

        // Generate recommendations based on the matching event for each user
        for (User user : allUsers) {
            Integer userId = user.getId();

            // Get user preferences
            String userPreferences = userService.getUserPreferences(userId);

            LOGGER.info("User {} preferences: {}", userId, userPreferences);

            // Generate recommendations for the user based on the matching event and
            // preferences
            for (Event event : matchingEvents) {
                Recommendation recommendation = new Recommendation();

                // Generate unique recommendationId
                String recommendationId = UUID.randomUUID().toString();
                recommendation.setRecommendationId(recommendationId);

                recommendation.setUserId(userId);
                recommendation.setEventId(event.getEventId());

                String reason = String.format(
                        "This event, titled '%s' and happening on %s, matches your preference for '%s' events. " +
                                "It has %d seats left. Here's what it's about: '%s'",
                        event.getEventTitle(), event.getEventDate(),
                        eventService.getEventTypeName(event.getEventTypeId()),
                        event.getEventCapacity(),
                        eventService.getEventDescription(event.getEventId()));
                recommendation.setRecommendationReason(reason);
                recommendation.setNotified(false);
                recommendation.setMessage("Recommendations generated successfully for User ID: " + userId);

                // Save the recommendation
                Recommendation savedRecommendation = recommendationRepository.save(recommendation);
                LOGGER.info("Saved recommendation: {}", savedRecommendation);
            }
        }
    }

}
