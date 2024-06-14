package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Organization;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {
    
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
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

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getOrgEvents(Organization o) {
        return eventRepository.findByOrganization(o);
    }

    public List<Event> getEventsEqAftDate(LocalDate d) {
        return eventRepository.findByDateGreaterThanEqual(d);
    }



}