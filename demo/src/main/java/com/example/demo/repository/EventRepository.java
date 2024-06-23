package com.example.demo.repository;

import com.example.demo.model.Event;
import com.example.demo.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByOrganisation(String orgId);

    List<Event> findByDateGreaterThanEqual(LocalDate d);

//    List<Event> findByDate(LocalDate d);
}
