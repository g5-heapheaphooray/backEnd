package com.example.demo.dto;

import com.example.demo.model.Event;

import java.util.List;

public class EventsListDTO {
    public List<Event> events;

    public EventsListDTO(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
