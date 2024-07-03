package com.example.demo.service;

import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
// import com.example.demo.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.repository.EventRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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
        currentEvent.setAddress(dto.getAddress());
        currentEvent.setSkills(dto.getSkills());
        currentEvent.setCauses(dto.getCauses());

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

    public CleanEventDTO getCleanEvent(Event e) {
        CleanEventDTO clean = new CleanEventDTO(e.getId(), e.getName(), e.getDate(), e.getStartTime(), e.getEndTime(), e.getOrganisation().getEmail(),
                e.getNeededManpowerCount(), e.getCurrentManpowerCount(), e.getLocation(), e.getDescription(), e.getType(), e.getAddress(), e.getPhotos(), e.getSkills(), e.getCauses());
        return clean;
    }

    public Event deleteEvent(String eventId) {
        Event event = getEvent(eventId);
        if (event == null) {
            return null;
        }

        Set<User> participants = event.getParticipants();
        for (User participant : participants) {
            participant.getEventsPart().remove(event);
            userRepository.save(participant); 
        }

        Organisation o = event.getOrganisation();
        Set<Event> eventsOrg = o.getEventsOrg();
        eventsOrg.remove(event);
        userRepository.save(o);

        try {
            eventRepository.delete(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return event;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getOrgEvents(Organisation o) {
        return eventRepository.findByOrganisation(o);
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

    public List<Volunteer> getEventParticipants(String eventId) {
        List<String> participantsEmail = eventRepository.findByIdWithParticipants(eventId);
        List<Volunteer> participants = new ArrayList<>();
        for (String pEmail : participantsEmail) {
            Volunteer v = (Volunteer) userRepository.findById(pEmail).orElse(null);
            if (v != null) {
                participants.add(v);
            }
        }
        return participants;
    }

    

}