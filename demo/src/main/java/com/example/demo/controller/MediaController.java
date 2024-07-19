package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.ComplaintService;
import com.example.demo.service.EventService;
import com.example.demo.service.MediaService;
import com.example.demo.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@CrossOrigin
@RequestMapping(value="/api/v1/media")
class MediaController {
    private final MediaService mediaService;
    private final EventService eventService;
    private final RewardService rewardService;
    private final ComplaintService complaintService;

    @Autowired
    public MediaController(MediaService mediaService, EventService eventService, RewardService rewardService, ComplaintService complaintService) {
        this.mediaService = mediaService;
        this.eventService = eventService;
        this.rewardService = rewardService;
        this.complaintService = complaintService;
    }

    @PostMapping(value = "/pfp/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> uploadPfp(@RequestPart("pfp") MultipartFile multipartImage) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        PfpMedia m = mediaService.savePfpImage(multipartImage, user);
        if (m == null) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/event-photos/upload/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<String> uploadEventPhotos(@RequestPart("eventPhotos") MultipartFile[] multipartImage, @PathVariable int eventId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event event = eventService.getEvent(eventId);
        if (!event.getOrganisation().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>("Unauthorised", HttpStatus.UNAUTHORIZED);
        }
        Set<EventMedia> ems = new HashSet<>();
        for (MultipartFile img : multipartImage) {
            EventMedia m = mediaService.saveEventImages(img, event);
            if (m == null) {
                return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
            }
            ems.add(m);
        }
        Event e = mediaService.saveEventImagesDb(ems, event);
        if (e == null) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/reward-category/reward-image/upload/{rewardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadRewardImage(@RequestPart("reward-image") MultipartFile multipartImage, @PathVariable int rewardId) throws Exception {
        RewardCategory rc = rewardService.getRewardCategory(rewardId);
        if (rc == null) {
            return new ResponseEntity<>("Reward category not found", HttpStatus.BAD_REQUEST);
        }
        RewardMedia m = mediaService.saveRewardImage(multipartImage, rc);
        if (m == null) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/complaint/complaint-image/upload/{complaintId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<String> uploadComplaintPhotos(@RequestPart("complaintPhotos") MultipartFile[] multipartImage, @PathVariable int complaintId) throws Exception {
        Complaint c = complaintService.getUncleanComplaint(complaintId);
        Set<ComplaintMedia> cms = new HashSet<>();
        for (MultipartFile img : multipartImage) {
            ComplaintMedia m = mediaService.saveComplaintImages(img, c);
            if (m == null) {
                return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
            }
            cms.add(m);
        }
        Complaint comp = mediaService.saveComplaintImagesDb(cms, c);
        if (comp == null) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }
}