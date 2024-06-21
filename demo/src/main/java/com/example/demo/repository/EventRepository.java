package com.example.demo.repository;

import com.example.demo.model.Event;
import com.example.demo.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByOrganization(String orgId);

    List<Event> findByDateGreaterThanEqual(LocalDate d);

//    List<Event> findByDate(LocalDate d);
}
