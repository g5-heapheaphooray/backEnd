package com.example.demo.dto;

import com.example.demo.dto.models.CleanEventDTO;

import java.util.List;

public class EventsListDTO {
    public List<CleanEventDTO> events;

    public EventsListDTO(List<CleanEventDTO> events) {
        this.events = events;
    }

    public List<CleanEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<CleanEventDTO> events) {
        this.events = events;
    }
}
