package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.Media;
import com.example.demo.model.Reward;
import com.example.demo.model.User;
import com.example.demo.repository.MediaRepository;
import com.example.demo.service.MediaService;
import com.example.demo.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/media")
class MediaController {
    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/pfp/upload")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> uploadPfp(@RequestParam MultipartFile multipartImage) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Media m = mediaService.savePfpImage(multipartImage, user);
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


    
}