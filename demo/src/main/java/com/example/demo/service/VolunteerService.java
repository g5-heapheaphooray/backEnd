package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.model.Volunteer;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, EventRepository eventRepository) {
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
    }

    public Volunteer updateHours(String id, double hours){
        Volunteer v = volunteerRepository.findById(id).orElse(null);

        if(v == null){
            return null;
        }
        v.setHours(v.getHours() + hours);
        return volunteerRepository.save(v);

    }

    public Volunteer updatePoints(String id, int points){
        Volunteer v = volunteerRepository.findById(id).orElse(null);

        if(v == null){
            return null;
        }
        v.setPoints(v.getPoints() + points);
        return volunteerRepository.save(v);
    }

    // public Volunteer updateEventsPart(String id, Event event){
    //     Volunteer v = volunteerRepository.findById(id).orElse(null);
    //     if(v == null){
    //         return null;
    //     }
    //     List<Event> eventPart = v.getEventsPart();
    //     eventPart.add(event);
    //     v.setEventsPart(eventPart);
    //     return volunteerRepository.save(v);

    // }

    public List<Event> getRegisteredEvents(String id){
        Volunteer v = volunteerRepository.findById(id).orElse(null);
        if(v == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(v.getEventsPart());
    }

    public Volunteer updateDetails(String id, Map<String, String> payload) {
        Volunteer v = volunteerRepository.findById(id).orElse(null);
        if(v == null){
            return null;
        }
        v.setContactNo(payload.get("contactNo"));
        v.setFullName(payload.get("fullName"));
        return volunteerRepository.save(v);
    }

    public Volunteer updateEventsParticipated(String eventId, String userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        Volunteer v = volunteerRepository.findById(userId).orElse(null);
        if (event == null || v == null) {
            return null;
        }

        Set<Event> eventList = v.getEventsPart();
        eventList.add(event);
        v.setEventsPart(eventList);
        return volunteerRepository.save(v);
    }

    public Volunteer unregisterEvent(String eventId, String userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        Volunteer v = volunteerRepository.findById(userId).orElse(null);
        if (event == null || v == null) {
            return null;
        }
        Set<Event> eventList = v.getEventsPart();
        eventList.remove(event);
        v.setEventsPart(eventList);
        return volunteerRepository.save(v);
    }

}
