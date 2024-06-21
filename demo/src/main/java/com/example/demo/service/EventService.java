package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Organisation;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    // private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository/*, UserRepository userRepository*/) {
        this.eventRepository = eventRepository;
        // this.userRepository = userRepository;
    }

    public Event updateEvent(CreateOppDTO dto, String eventId) {
        Event currentEvent = eventRepository.findById(eventId).orElse(null);
        
        if (currentEvent == null) {
            return null;
        }

        currentEvent.setName(dto.getName());
        currentEvent.setDate(dto.getDate());
        currentEvent.setStartTime(dto.getStartTime());
        currentEvent.setEndTime(dto.getEndTime());
        currentEvent.setNeededManpowerCount(dto.getManpowerCount());
        currentEvent.setLocation(dto.getLocation());
        currentEvent.setDescription(dto.getDescription());
        currentEvent.setType(dto.getType());

        return eventRepository.save(currentEvent);
    }

    public Event createEvent(Event event, User o) {
        // System.out.println("creating event now");
        // System.out.println(o.getUsername());
        o.getEventsOrg().add(event);
        // System.out.println(o.getEventsOrg());
//        o.addEventOrg(event);
        // userRepository.save(o);
        // System.out.println("hello creating event");
        return eventRepository.save(event);
    }

    public Event getEvent(String eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public Event deleteEvent(String eventId) {
        Event event = getEvent(eventId);
        if (event == null) {
            return null;
        }
        eventRepository.delete(event);
        return event;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getOrgEvents(String orgId) {
        return eventRepository.findByOrganisation(orgId);
    }

    public List<Event> getEventsEqAftDate(LocalDate d) {
        return eventRepository.findByDateGreaterThanEqual(d);
    }

    // public Event updateEventParticipants(String eventId, User user) {
    //     Event e = eventRepository.findById(eventId).orElse(null);
        
    //     if (e == null) {
    //         return null;
    //     }

    //     e.getParticipants().add(user);
    //     System.out.println(e.getParticipants());
    //     return eventRepository.save(e);
    // }

}