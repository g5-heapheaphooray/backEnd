package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Comparator;

@Entity
@Table(name = "event_media")
public class EventMedia extends Media implements Comparable<EventMedia> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

//    @Column(name = "cover")
//    private boolean cover;

    public EventMedia() {}

//    public EventMedia(String filename, String filepath, Event event, boolean cover) {
//        super(filename, filepath);
//        this.event = event;
//        this.cover = cover;
//    }

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

//    public boolean isCover() {
//        return cover;
//    }
//
//    public void setCover(boolean cover) {
//        this.cover = cover;
//    }

    public int compareTo(EventMedia em) {
        return this.getId() - em.getId();
    }
}
