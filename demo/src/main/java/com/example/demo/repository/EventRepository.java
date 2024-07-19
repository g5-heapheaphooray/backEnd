package com.example.demo.repository;

import com.example.demo.model.Event;
import com.example.demo.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByOrganisation(Organisation org);

    List<Event> findByDateGreaterThanEqual(LocalDate d);
   
    @Query(value = "SELECT user_id FROM events_part WHERE events_part.event_id = :eventId", nativeQuery = true)
    List<String> findByIdWithParticipants(@Param("eventId") String eventId);
}
