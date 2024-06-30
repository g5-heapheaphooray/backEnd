package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "event_media")
public class EventMedia extends Media {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public EventMedia() {}

    public EventMedia(String filename, String filepath, Event event) {
        super(filename, filepath);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
