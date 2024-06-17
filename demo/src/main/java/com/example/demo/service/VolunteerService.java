package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.model.Volunteer;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
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

    public Volunteer updateDetails(String id, Map<String, String> payload) {
        Volunteer v = volunteerRepository.findById(id).orElse(null);
        if(v == null){
            return null;
        }
        v.setContactNo(payload.get("contactNo"));
        v.setFullName(payload.get("fullName"));
        return volunteerRepository.save(v);
    }

}
