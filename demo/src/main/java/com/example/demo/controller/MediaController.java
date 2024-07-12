package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.MediaRepository;
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

    @Autowired
    public MediaController(MediaService mediaService, EventService eventService, RewardService rewardService) {
        this.mediaService = mediaService;
        this.eventService = eventService;
        this.rewardService = rewardService;
    }

    @PostMapping(value = "/pfp/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> uploadPfp(@RequestPart("pfp") MultipartFile multipartImage) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(multipartImage.getOriginalFilename());
        PfpMedia m = mediaService.savePfpImage(multipartImage, user);
        if (m == null) {
            return new ResponseEntity<>("Image upload failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    }

    @GetMapping("/pfp/get")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getPfp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        byte[] data = null;
        try {
            System.out.println(user.getPfp().getFilepath());
            data = mediaService.getMedia(user.getPfp().getFilepath());
        } catch (Exception e) {
            return new ResponseEntity<>("idk", HttpStatus.NOT_FOUND);
        }
        if (data == null) {
            return new ResponseEntity<>("idk2", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/event-photos/upload/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<String> uploadEventPhotos(@RequestPart("eventPhotos") MultipartFile[] multipartImage, @PathVariable String eventId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event event = eventService.getEvent(eventId);
        if (!event.getOrganisation().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>("Unauthorised", HttpStatus.UNAUTHORIZED);
        }
        Set<EventMedia> ems = new HashSet<>();
        System.out.println(multipartImage.length);
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

    @GetMapping("/event-photos/get/{eventId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> uploadEventPhotos(@PathVariable String eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event event = eventService.getEvent(eventId);
        if (!event.getOrganisation().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>("Unauthorised", HttpStatus.UNAUTHORIZED);
        }
        Set<EventMedia> ems = event.getPhotos();
        List<byte[]> res = new ArrayList<>();
        for (EventMedia em : ems) {
            byte[] data = null;
            try {
                data = mediaService.getMedia(em.getFilepath());
            } catch (Exception e) {
                return new ResponseEntity<>("idk", HttpStatus.NOT_FOUND);
            }
            if (data == null) {
                return new ResponseEntity<>("idk2", HttpStatus.NOT_FOUND);
            } else {
                res.add(data);
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/reward-category/reward-image/upload/{rewardId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadRewardImage(@RequestPart("reward-image") MultipartFile multipartImage, @PathVariable int rewardId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
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

    @GetMapping("/reward-category/reward-image/upload/{rewardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getRewardMedia(@PathVariable int rewardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        byte[] data = null;
        RewardCategory rc = rewardService.getRewardCategory(rewardId);
        if (rc == null) {
            return new ResponseEntity<>("Reward category not found", HttpStatus.BAD_REQUEST);
        }
        try {
            data = mediaService.getMedia(rc.getRewardMedia().getFilepath());
        } catch (Exception e) {
            return new ResponseEntity<>("idk", HttpStatus.NOT_FOUND);
        }
        if (data == null) {
            return new ResponseEntity<>("idk2", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
}