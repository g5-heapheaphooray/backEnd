package com.example.demo.data;

import com.example.demo.dto.CreateComplaintDTO;
import com.example.demo.dto.CreateOppDTO;
import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.dto.RegisterAdminDTO;
import com.example.demo.dto.RegisterOrganisationDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanRewardsCategoryDTO;
import com.example.demo.model.Event;
import com.example.demo.model.Organisation;
import com.example.demo.model.RewardCategory;
import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.OrganisationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.service.ComplaintService;
import com.example.demo.service.EventService;
import com.example.demo.service.RewardService;
import com.example.demo.service.UserService;
import com.example.demo.service.VolunteerService;

import io.jsonwebtoken.impl.lang.Services;
import jakarta.transaction.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.print.attribute.standard.MediaSize.Other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PopulateData {
    private final Random rand = new Random();
    private static int NUM_OF_USERS = 20;
    private static int NUM_OF_EVENTS = 3;
    private static int NUM_OF_EVENT_REGISTRATIONS = 3;
    private static int NUM_OF_REWARDS = 5;
    private static int NUM_OF_COMPLAINTS = 10;

    private final UserService userService;
    private final VolunteerService volunteerService;
    private final EventService eventService;
    private final RewardService rewardService;
    private final ComplaintService complaintService;
    
    private final UserRepository userRepository;
    private final OrganisationRepository organisationRepository;
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;

    @Autowired
    public PopulateData(UserService userService, VolunteerService volunteerService, EventService eventService, RewardService rewardService, ComplaintService complaintService, UserRepository userRepository, OrganisationRepository organisationRepository, VolunteerRepository volunteerRepository, EventRepository eventRepository) {
        this.userService = userService;
        this.volunteerService = volunteerService;
        this.eventService = eventService;
        this.rewardService = rewardService;
        this.complaintService = complaintService;
        this.userRepository = userRepository;
        this.organisationRepository = organisationRepository;
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
    }

    enum UserType {
        ADMIN, 
        VOLUNTEER, 
        ORGANISATION
    }
    
    // Create users
    public void createSampleUsers() {
        System.out.println("Creating sample users");
        for (int i = 1; i <= NUM_OF_USERS; i++) {
            String[] userType = {"Volunteer", "Organisation"}; 
            switch (userType[rand.nextInt(userType.length)]) {
                case "Volunteer":
                    String username = "Volunteer" + i;
                    RegisterVolunteerDTO volunteerDTO = new RegisterVolunteerDTO(username + "@mail.com", username, "123", "12345678", 'M', LocalDate.now());
                    userService.createVolunteer(volunteerDTO);
                    break;
                case "Organisation":
                    username = "Organisation" + i;
                    RegisterOrganisationDTO orgDTO = new RegisterOrganisationDTO(username + "@mail.com", username, "123", "12345678", "location", "website", "description");
                    userService.createVerifiedOrganisation(orgDTO);
                    break;
                default:
                    break;
            }
        }
    }

    // Create events
    // Register volunteers for events
    public void createSampleEvents() {
        System.out.println("Creating sample events");
        List<Organisation> orgs = organisationRepository.findAll();
        String[] causes = {"Animal Welfare", "Arts", "Children", "Community", "Drug Awareness", "Education", 
        "Eldercare", "Environment and Water", "Families", "Health", "Heritage", "Humanitarian", "Mental Health", 
        "Migrant Workers", "Other", "Rehabilitation & Reintegration", "Safety & Security", "Social Services", "Special Needs/Disabilities"};
        String[] skills = {"Art & Craft", "Befriending", "Coaching & Mentoring", "Counselling", "Dialect-Speaking", "Emcee skills", 
        "Entrepreneurship", "Event Management", "Facilitation", "First-aid", "Graphic Design", "Language Translation", "Music",
        "Photography", "Reading", "Sign Language", "Social Media Execution", "Software Development", "Sports", "Tutoring", "Videography", "Web Design", "Others"};
        for (Organisation org : orgs) {
            int randNum = rand.nextInt(NUM_OF_EVENTS);
            String orgName = org.getFullName();
            for (int i = 0; i < randNum; i++) {
                String eventName = orgName + "_Event_" + (i+1);
                Set<String> causesSet = new HashSet<String>();
                Set<String> skillsSet = new HashSet<String>();
                causesSet.addAll(List.of(causes[rand.nextInt(causes.length)], causes[rand.nextInt(causes.length)]));
                skillsSet.addAll(List.of(skills[rand.nextInt(skills.length)], skills[rand.nextInt(skills.length)]));
                CreateOppDTO oppDTO = new CreateOppDTO(eventName, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), 10, "location", "description", "type", "address", new ArrayList<>(skillsSet), new ArrayList<>(causesSet));
                eventService.createEvent(oppDTO, org);
            }
        }

        System.out.println("Registering volunteers for events");
        List<Event> events = eventRepository.findAll();
        List<Volunteer> vols = volunteerRepository.findAll();
        for (Volunteer vol : vols) {
            int randNum = rand.nextInt(NUM_OF_EVENT_REGISTRATIONS);
            for (int i = 0; i < randNum; i++) {
                Event event = events.get(rand.nextInt(events.size()));
                if (vol.getEventsPart().contains(event)) {
                    continue;
                }
                vol.getEventsPart().add(event);
                event.incCurrentManpowerCount();
            }
        }
    }

    // Create rewards
    public void createSampleRewards() {
        System.out.println("Creating sample rewards");
        for (int i = 1; i <= NUM_OF_REWARDS; i++) {
            CreateRewardDTO rewardDTO = new CreateRewardDTO("Reward" + i, 10, "type", "description", 100);
            rewardService.createRewardCategory(rewardDTO);
        }
    }

    // Create complaints
    public void createSampleComplaints() {
        String[] statuses = {"Pending", "Resolved", "Rejected"};
        List<User> users = userRepository.findAll();
        for (int i = 1; i <= NUM_OF_COMPLAINTS; i++) {
            String status = statuses[rand.nextInt(statuses.length)];
            CreateComplaintDTO complaintDTO = new CreateComplaintDTO("Complaint" + i, "description", status);
            User chosenUser = users.get(rand.nextInt(NUM_OF_USERS));
            complaintService.createComplaintNoMail(complaintDTO, chosenUser);
        }
    }

    public void createFixedData() {
        System.out.println("Creating fixed data");
        RegisterAdminDTO adminDTO = new RegisterAdminDTO("admin@mail.com", "Admin", "123", "12345678");
        userService.createAdmin(adminDTO);

        RegisterVolunteerDTO orgDTO = new RegisterVolunteerDTO("vol@mail.com", "Volunteer", "123", "12345678", 'M', LocalDate.now());
        User vol = userService.createVolunteer(orgDTO);
        volunteerService.updatePoints((Volunteer) vol, 100);

        RegisterOrganisationDTO volDTO = new RegisterOrganisationDTO("org@mail.com", "Organisation", "123", "12345678", "location", "website", "description");
        User org = userService.createVerifiedOrganisation(volDTO);

        CreateOppDTO oppDTO = new CreateOppDTO("Event", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), 10, "location", "description", "type", "address", List.of("Art & Craft", "Befriending"), List.of("Arts", "Eldercare"));
        CleanEventDTO cleanEvent = eventService.createEvent(oppDTO, (Organisation) org);
        Event event = eventService.getEvent(cleanEvent.getId());
        vol.getEventsPart().add(event);
        event.incCurrentManpowerCount();

        CreateRewardDTO rewardDTO = new CreateRewardDTO("Reward", 10, "type", "description", 6);
        CleanRewardsCategoryDTO cleanRc = rewardService.createRewardCategory(rewardDTO);
        RewardCategory rc = rewardService.getRewardCategory(cleanRc.getId());

        CreateComplaintDTO complaintDTO = new CreateComplaintDTO("Complaint", "description", "Pending");
        complaintService.createComplaintNoMail(complaintDTO, vol);

        try {
            rewardService.uploadBarcodes(rc, new FileInputStream("./src/main/java/com/example/demo/data/barcodes.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        rewardService.redeemReward(rc, (Volunteer) vol);
    }

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void populateData() {
        // If there are already users in the database, don't populate
        if (userRepository.count() > 0) {
            return;
        }

        createFixedData();
        createSampleUsers();
        createSampleEvents();
        createSampleRewards();
        createSampleComplaints();
    }
}
