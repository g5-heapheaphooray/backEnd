package com.example.demo.service;

import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
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
        // System.out.println("creating event now");
        // System.out.println(o.getUsername());
        o.getEventsOrg().add(e);
        // System.out.println(o.getEventsOrg());
        // o.addEventOrg(event);
        // userRepository.save(o);
        // System.out.println("hello creating event");
        return getCleanEvent(eventRepository.save(e));
    }

    public Event getEvent(int eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public CleanEventDTO getCleanEvent(Event e) {
        Set<EventMedia> ems = e.getPhotos();
        List<String> emsFp = new ArrayList<>();
        List<byte[]> emsBytes = new ArrayList<>();
        for (EventMedia em : ems) {
            try {
//                emsBytes.add(mediaService.getMedia(em.getFilepath()));
                emsFp.add(mediaService.getObjectUrl(em.getFilepath()));
            } catch (Exception ex) {
            }

        }
        emsFp.sort(null);
        CleanEventDTO clean = new CleanEventDTO(e.getId(), e.getName(), e.getDate(), e.getStartTime(), e.getEndTime(),
                e.getOrganisation().getEmail(),
                e.getNeededManpowerCount(), e.getCurrentManpowerCount(), e.getLocation(), e.getDescription(),
                e.getType(), e.getAddress(), e.getSkills(), e.getCauses(), emsFp);
        return clean;
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

    // public Event updateEventParticipants(String eventId, User user) {
    // Event e = eventRepository.findById(eventId).orElse(null);

    // if (e == null) {
    // return null;
    // }

    // e.getParticipants().add(user);
    // System.out.println(e.getParticipants());
    // return eventRepository.save(e);
    // }

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

        List<CleanVolunteerDTO> newCleanVol = new ArrayList<>(dto);
        Set<User> currentParticipants = e.getParticipants();
        Set<User> participants = new HashSet<>();
        for (CleanVolunteerDTO vol : dto) {
            System.out.println(vol.getEmail());
            User user = userRepository.findById(vol.getEmail()).orElse(null);
            if (user != null && currentParticipants.contains(user)) {
                participants.add(user);
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