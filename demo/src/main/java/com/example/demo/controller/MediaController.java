package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
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

    @Autowired
    public MediaController(MediaService mediaService, EventService eventService) {
        this.mediaService = mediaService;
        this.eventService = eventService;
    }

    @PostMapping(value = "/pfp/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> uploadPfp(@RequestParam("pfp") MultipartFile multipartImage) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(multipartImage.getOriginalFilename());
        PfpMedia m = mediaService.savePfpImage(multipartImage, user);
        if (m == null) {
            return new ResponseEntity<>(new ResponseDTO("Image upload failed", 400), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ResponseDTO("Image uploaded successfully", 200), HttpStatus.OK);
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

    @PostMapping(value = "/event-photos/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> uploadEventPhotos(@RequestParam("eventPhotos") MultipartFile[] multipartImage, @RequestParam("eventId") String eventId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event e = eventService.getEvent(eventId);
        for (MultipartFile img : multipartImage) {
            EventMedia m = mediaService.saveEventImages(img, e);
            if (m == null) {
                return new ResponseEntity<>(new ResponseDTO("Image upload failed", 400), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ResponseDTO("Image uploaded successfully", 200), HttpStatus.OK);
    }

    @GetMapping("/event-photos/get/{eventId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> uploadEventPhotos(@PathVariable String eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event event = eventService.getEvent(eventId);
        List<EventMedia> ems = event.getPhotos();
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



    
}