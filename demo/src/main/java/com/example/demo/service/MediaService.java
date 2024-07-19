package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;

import com.example.demo.repository.*;
import com.obs.services.model.AccessControlList;
import com.obs.services.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.*;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import java.io.File;

@Service
public class MediaService {
    
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RewardCategoryRepository rewardCategoryRepository;
    private final ComplaintRepository complaintRepository;

//    private final String rootUploadDirectory = "./media/";

    @Value("${obs.accesskeyid}")
    private String accessKey;

    @Value("${obs.secretaccesskeyid}")
    private String secretAccessKey;

    @Value("${obs.endpoint}")
    private String endpoint;

    @Value("${obs.bucketname}")
    private String bucketname;


    @Autowired
    public MediaService(MediaRepository mediaRepository, UserRepository userRepository, EventRepository eventRepository, RewardCategoryRepository rewardCategoryRepository, ComplaintRepository complaintRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.rewardCategoryRepository = rewardCategoryRepository;
        this.complaintRepository = complaintRepository;
    }

    public PfpMedia savePfpImage(MultipartFile multipartImage, User user) {
        PfpMedia pfp = new PfpMedia();
        String filename = "PFP-" + user.getEmail() + ".png";
        pfp.setFilename(filename);
        pfp.setUser(user);
        String filepath = writeToObs(multipartImage, filename, "Profile-Media");

        if (filepath == null) {
            return null;
        }
        pfp.setFilepath(filepath);
        if (!user.getPfp().getFilename().equals("default.png")) {
            try {
                deleteFromObs(user.getPfp().getFilepath());
//                deleteMedia(user.getPfp());
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

    public Event saveEventImagesDb(Set<EventMedia> ems, Event event) {
        event.setPhotos(ems);
        return eventRepository.save(event);
    }

    public EventMedia saveEventImages(MultipartFile multipartImage, Event event, boolean first) {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
        EventMedia em = new EventMedia();
        String filename = "EM-" + event.getId() + ".png";
        em.setFilename(filename);
        em.setEvent(event);
        em.setCover(first);
        String filepath = writeToObs(multipartImage, filename, "Event-Media");
        if (filepath == null) {
            return null;
        }
        em.setFilepath(filepath);
        // Set<EventMedia> ems = event.getPhotos();
        // ems.add(em);
        // event.setPhotos(ems);
        // eventRepository.save(event);
        return mediaRepository.save(em);
    }

    public RewardMedia saveRewardImage(MultipartFile multipartImage, RewardCategory rc) {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
        RewardMedia rm = new RewardMedia();
        String filename = "RM-" + rc.getId() + ".png";
        rm.setFilename(filename);
        rm.setRewardCategory(rc);
        String filepath = writeToObs(multipartImage, filename, "Reward-Media");
        if (filepath == null) {
            return null;
        }
        rm.setFilepath(filepath);
        if (rc.getRewardMedia() != null) {
            try {
                deleteFromObs(rc.getRewardMedia().getFilepath());
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

    public ComplaintMedia saveComplaintImages(MultipartFile multipartImage, Complaint c) {
        ComplaintMedia cm = new ComplaintMedia();
        String filename = "CM-" + c.getId() + ".png";
        cm.setFilename(filename);
        cm.setComplaint(c);
        String filepath = writeToObs(multipartImage, filename, "Complaint-Media");
        if (filepath == null) {
            return null;
        }
        cm.setFilepath(filepath);
        return mediaRepository.save(cm);
    }

    public Complaint saveComplaintImagesDb(Set<ComplaintMedia> cms, Complaint c) {
        c.setPhotos(cms);
        return complaintRepository.save(c);
    }

//    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile, String filename) throws IOException {
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
//
//        Path uploadPath = Path.of(rootUploadDirectory + uploadDirectory);
//        Path filePath = uploadPath.resolve(uniqueFileName);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        String uniqueFilePath = rootUploadDirectory + uploadDirectory + "/" + uniqueFileName;
//        return uniqueFilePath;
//    }
//
//    // To view an image
//    public byte[] getMedia(String filepath) throws IOException {
//        Path currentRelativePath = Paths.get("");
//        System.out.println(currentRelativePath.toAbsolutePath().toString());
//        Path imagePath = Path.of(filepath);
//
//        if (Files.exists(imagePath)) {
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//            return Base64.getEncoder().encode(imageBytes);
//        }
//        return null; // Handle missing images
//    }
//
//    // Delete an image
//    public String deleteMedia(Media m) throws IOException {
//        Path imagePath = Path.of(m.getFilepath());
//        System.out.println(imagePath);
//        if (Files.exists(imagePath)) {
//            Files.delete(imagePath);
////            mediaRepository.delete(m);
//            return "Success";
//        } else {
//            return "Failed"; // Handle missing images
//        }
//    }

    public String writeToObs(MultipartFile imageFile, String filename, String type) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        String objectKey = type + "/" + uniqueFileName;
        System.out.println("hello");
        try {
            ObsClient obsClient = new ObsClient(accessKey, secretAccessKey,endpoint);
            PutObjectRequest request = new PutObjectRequest();
            request.setBucketName(bucketname);
            request.setInput(imageFile.getInputStream());
            request.setObjectKey(objectKey);
//            request.setFile(new File("./media/pfp/default.png"));
            request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
            obsClient.putObject(request);
            System.out.println("putObject successfully");
            return objectKey;
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // Request failed. Print the HTTP status code.
            System.out.println("HTTP Code:" + e.getResponseCode());
            // Request failed. Print the server-side error code.
            System.out.println("Error Code:" + e.getErrorCode());
            // Request failed. Print the error details.
            System.out.println("Error Message:" + e.getErrorMessage());
            // Request failed. Print the request ID.
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // Print other error information.
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFromObs(String objectKey) {
        try {
            ObsClient obsClient = new ObsClient(accessKey, secretAccessKey,endpoint);
            // Delete the object.
            obsClient.deleteObject(bucketname, objectKey);
            System.out.println("deleteObject successfully");
        } catch (ObsException e) {
            System.out.println("deleteObject failed");
            // Request failed. Print the HTTP status code.
            System.out.println("HTTP Code:" + e.getResponseCode());
            // Request failed. Print the server-side error code.
            System.out.println("Error Code:" + e.getErrorCode());
            // Request failed. Print the error details.
            System.out.println("Error Message:" + e.getErrorMessage());
            // Request failed. Print the request ID.
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("deleteObject failed");
            // Print other error information.
            e.printStackTrace();
        }
        return null;
    }

    public String getObjectUrl(String objectKey) {
        return "https://" + bucketname + "." + endpoint + "/" + objectKey;
    }




}
