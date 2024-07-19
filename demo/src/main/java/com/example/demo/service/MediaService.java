package com.example.demo.service;

import java.util.Set;
import java.util.UUID;

import com.example.demo.repository.*;
import com.obs.services.model.AccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.*;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;

@Service
public class MediaService {
    
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RewardCategoryRepository rewardCategoryRepository;
    private final ComplaintRepository complaintRepository;

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

    public EventMedia saveEventImages(MultipartFile multipartImage, Event event) {
        EventMedia em = new EventMedia();
        String filename = "EM-" + event.getId() + ".png";
        em.setFilename(filename);
        em.setEvent(event);
        String filepath = writeToObs(multipartImage, filename, "Event-Media");
        if (filepath == null) {
            return null;
        }
        em.setFilepath(filepath);
        return mediaRepository.save(em);
    }

    public RewardMedia saveRewardImage(MultipartFile multipartImage, RewardCategory rc) {
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

    public String writeToObs(MultipartFile imageFile, String filename, String type) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        String objectKey = type + "/" + uniqueFileName;
        try {
            ObsClient obsClient = new ObsClient(accessKey, secretAccessKey,endpoint);
            PutObjectRequest request = new PutObjectRequest();
            request.setBucketName(bucketname);
            request.setInput(imageFile.getInputStream());
            request.setObjectKey(objectKey);
            request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
            obsClient.putObject(request);
            return objectKey;
        } catch (ObsException e) {
            System.out.println("putObject failed");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("putObject failed");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String deleteFromObs(String objectKey) {
        try {
            ObsClient obsClient = new ObsClient(accessKey, secretAccessKey,endpoint);
            // Delete the object.
            obsClient.deleteObject(bucketname, objectKey);
        } catch (ObsException e) {
            System.out.println("deleteObject failed");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("deleteObject failed");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getObjectUrl(String objectKey) {
        return "https://" + bucketname + "." + endpoint + "/" + objectKey;
    }
}
