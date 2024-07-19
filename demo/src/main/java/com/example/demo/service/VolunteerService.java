package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.User;
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
    private final MediaService mediaService;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, EventRepository eventRepository, MediaService mediaService) {
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
        this.mediaService = mediaService;
    }

    public CleanVolunteerDTO getCleanVol(Volunteer v) {
        return new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), mediaService.getObjectUrl(v.getPfp().getFilepath()));
    }

    public List<CleanVolunteerDTO> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        List<CleanVolunteerDTO> cleanVols = new ArrayList<>();
        for (Volunteer v : volunteers) {
            cleanVols.add(getCleanVol(v));
        }
        return cleanVols;
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

    public Volunteer updatePoints(Volunteer v, int points){

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

    public Volunteer updateDetails(RegisterVolunteerDTO dto, User user) {
        if (user instanceof Volunteer v) {
            v.setContactNo(dto.getContactNo());
            v.setFullName(dto.getFullName());
            return volunteerRepository.save(v);
        }
        return null;
    }

    public Volunteer registerEvent(int eventId, Volunteer v) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null || v == null) {
            return null;
        }
        Set<Event> eventList = v.getEventsPart();
        eventList.add(event);
        v.setEventsPart(eventList);
        event.incCurrentManpowerCount();
        eventRepository.save(event);
        return volunteerRepository.save(v);
    }

    public Volunteer unregisterEvent(int eventId, String userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        Volunteer v = volunteerRepository.findById(userId).orElse(null);
        if (event == null || v == null) {
            return null;
        }
        Set<Event> eventList = v.getEventsPart();
        eventList.remove(event);
        v.setEventsPart(eventList);
        event.decCurrentManpowerCount();
        eventRepository.save(event);
        return volunteerRepository.save(v);
    }

}
