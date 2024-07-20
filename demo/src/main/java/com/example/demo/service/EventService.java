package com.example.demo.service;

import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.repository.EventRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final MediaService mediaService;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, MediaService mediaService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.mediaService = mediaService;
    }

    public Event updateEvent(CreateOppDTO dto, int eventId) {
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

    public CleanEventDTO createEvent(CreateOppDTO dto, Organisation o) {
        Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(),
        dto.getLocation(), dto.getDescription(), dto.getType(), dto.getAddress(), dto.getSkills(), dto.getCauses());
        int hours = (int) Duration.between(e.getStartTime(), e.getEndTime()).toHours();
        e.setPoints(hours * 10);
        o.getEventsOrg().add(e);
        return getCleanEvent(eventRepository.save(e));
    }

    public Event createDummyEvent(CreateOppDTO dto, Organisation o) {
        Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(),
                dto.getLocation(), dto.getDescription(), dto.getType(), dto.getAddress(), dto.getSkills(), dto.getCauses());
        int hours = (int) Duration.between(e.getStartTime(), e.getEndTime()).toHours();
        e.setPoints(hours * 10);
        o.getEventsOrg().add(e);
        Set<EventMedia> photos = new HashSet<>();
        photos.add(new EventMedia("default.png", "Event-Media/default.png", e));
        e.setPhotos(photos);
        return eventRepository.save(e);
    }

    public Event getEvent(int eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public CleanEventDTO getCleanEvent(Event e) {
        List<EventMedia> ems = new ArrayList<>();
        ems.addAll(e.getPhotos());
        Collections.sort(ems);
        List<String> emsFp = new ArrayList<>();
        for (EventMedia em : ems) {
            try {
                emsFp.add(mediaService.getObjectUrl(em.getFilepath()));
            } catch (Exception ex) {
            }

        }
        return new CleanEventDTO(e.getId(), e.getName(), e.getDate(), e.getStartTime(), e.getEndTime(),
                e.getOrganisation().getEmail(),
                e.getNeededManpowerCount(), e.getCurrentManpowerCount(), e.getLocation(), e.getDescription(),
                e.getType(), e.getAddress(), e.getSkills(), e.getCauses(), emsFp, e.getPoints(), e.getAttendance());
    }

    public Event deleteEvent(int eventId) {
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

    public List<CleanEventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<CleanEventDTO> cleanEvents = new ArrayList<>();
        for (Event e : events) {
            cleanEvents.add(getCleanEvent(e));
        }
        return cleanEvents;
    }

    public List<CleanEventDTO> getOrgEvents(Organisation o) {
        List<Event> events = eventRepository.findByOrganisation(o);
        List<CleanEventDTO> cleanEvents = new ArrayList<>();
        for (Event e : events) {
            cleanEvents.add(getCleanEvent(e));
        }
        return cleanEvents;
    }

    public List<Event> getEventsEqAftDate(LocalDate d) {
        return eventRepository.findByDateGreaterThanEqual(d);
    }

    public List<CleanVolunteerDTO> getEventParticipants(int eventId) {
        Event e = eventRepository.findById(eventId).orElse(null);
        if (e == null) {
            return new ArrayList<>();
        }

        Set<User> volList = e.getParticipants();
        List<CleanVolunteerDTO> cleanVolList = new ArrayList<>();
        for (User vol : volList) {
            if (vol instanceof Volunteer v) {
                cleanVolList.add(
                        new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(),
                                v.getGender(), v.getDob(), v.getHours(), v.getPoints(), mediaService.getObjectUrl(v.getPfp().getFilepath())));
            }
        }
        return cleanVolList;
    }

    public List<CleanVolunteerDTO> setEventParticipants(int eventId, List<CleanVolunteerDTO> dto) {
        Event e = eventRepository.findById(eventId).orElse(null);
        if (e == null) {
            return new ArrayList<>();
        }

        e.setAttendance(true);

        List<CleanVolunteerDTO> newCleanVol = new ArrayList<>(dto);
        Set<User> currentParticipants = e.getParticipants();
        Set<User> participants = new HashSet<>();
        for (CleanVolunteerDTO vol : dto) {
            User user = userRepository.findById(vol.getEmail()).orElse(null);
            if (user != null && currentParticipants.contains(user)) {
                participants.add(user);
                Volunteer v = (Volunteer) user;
                v.setPoints(v.getPoints() + e.getPoints());
            } else if (!currentParticipants.contains(user)) {
                newCleanVol.remove(vol);
            }
        }

        for (User vol : currentParticipants) {
            if (!participants.contains(vol)) {
                Set<Event> eventsPart = vol.getEventsPart();
                eventsPart.remove(e);
                vol.setEventsPart(eventsPart);
                userRepository.save(vol);
            }
        }

        e.setParticipants(participants);
        eventRepository.save(e);
        
        return newCleanVol;
    }
}