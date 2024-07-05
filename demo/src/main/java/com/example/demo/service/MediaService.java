package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RewardCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.*;
import com.example.demo.repository.MediaRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MediaService {
    
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RewardCategoryRepository rewardCategoryRepository;
    private final String rootUploadDirectory = "./media/";

    @Autowired
    public MediaService(MediaRepository mediaRepository, UserRepository userRepository, EventRepository eventRepository, RewardCategoryRepository rewardCategoryRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.rewardCategoryRepository = rewardCategoryRepository;
    }

    public PfpMedia savePfpImage(MultipartFile multipartImage, User user) {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
        PfpMedia pfp = new PfpMedia();
        String filename = "PFP-" + user.getEmail() + ".png";
        pfp.setFilename(filename);
        pfp.setUser(user);
        String filepath = null;
        try {
            filepath = saveImageToStorage("pfp", multipartImage, filename);
        } catch (Exception e) {
            return null;
        }
        if (filepath == null) {
            return null;
        }
        pfp.setFilepath(filepath);
        if (!user.getPfp().getFilename().equals("default.png")) {
            try {
                deleteMedia(user.getPfp());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        try {
            PfpMedia oldpfp = user.getPfp();
            mediaRepository.save(pfp);
            user.setPfp(pfp);
            userRepository.save(user);
            mediaRepository.delete(oldpfp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return pfp;
    }

    public EventMedia saveEventImages(MultipartFile multipartImage, Event event) {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
        EventMedia em = new EventMedia();
        String filename = "EM-" + event.getId() + ".png";
        em.setFilename(filename);
        em.setEvent(event);
        String filepath = null;
        try {
            filepath = saveImageToStorage("event-photos", multipartImage, filename);
        } catch (Exception e) {
            return null;
        }
        if (filepath == null) {
            return null;
        }
        em.setFilepath(filepath);
        Set<EventMedia> ems = event.getPhotos();
        ems.add(em);
        event.setPhotos(ems);
        eventRepository.save(event);
        return mediaRepository.save(em);
    }

    public RewardMedia saveRewardImage(MultipartFile multipartImage, RewardCategory rc) {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
        RewardMedia rm = new RewardMedia();
        String filename = "RM-" + rc.getId() + ".png";
        rm.setFilename(filename);
        rm.setRewardCategory(rc);
        String filepath = null;
        try {
            filepath = saveImageToStorage("reward-media", multipartImage, filename);
        } catch (Exception e) {
            return null;
        }
        if (filepath == null) {
            return null;
        }
        rm.setFilepath(filepath);
        if (rc.getRewardMedia() != null) {
            try {
                deleteMedia(rc.getRewardMedia());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        try {
            RewardMedia oldrm = rc.getRewardMedia();
            mediaRepository.save(rm);
            rc.setRewardMedia(rm);
            rewardCategoryRepository.save(rc);
            if (oldrm != null) {
                mediaRepository.delete(oldrm);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return rm;
    }

    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile, String filename) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;

        Path uploadPath = Path.of(rootUploadDirectory + uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        String uniqueFilePath = rootUploadDirectory + uploadDirectory + "/" + uniqueFileName;
        return uniqueFilePath;
    }

    // To view an image
    public byte[] getMedia(String filepath) throws IOException {
        Path currentRelativePath = Paths.get("");
        System.out.println(currentRelativePath.toAbsolutePath().toString());
        Path imagePath = Path.of(filepath);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encode(imageBytes);
        } 
        return null; // Handle missing images
    }

    // Delete an image
    public String deleteMedia(Media m) throws IOException {
        Path imagePath = Path.of(m.getFilepath());
        System.out.println(imagePath);
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
//            mediaRepository.delete(m);
            return "Success";
        } else {
            return "Failed"; // Handle missing images
        }
    }


}
