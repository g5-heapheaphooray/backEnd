package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.model.*;
import com.example.demo.repository.MediaRepository;
import com.example.demo.repository.RewardRepository;
import com.example.demo.repository.UserRepository;

@Service
public class MediaService {
    
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final String rootUploadDirectory = "./media/";

    @Autowired
    public MediaService(MediaRepository mediaRepository, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }

    public PfpMedia savePfpImage(MultipartFile multipartImage, User user) {
        Path currentRelativePath = Paths.get("");
        System.out.println(currentRelativePath.toAbsolutePath().toString());
        PfpMedia pfp = new PfpMedia();
        pfp.setFilename(multipartImage.getName());
        pfp.setUser(user);
        String filepath = null;
        try {
            filepath = saveImageToStorage("pfp", multipartImage);
        } catch (Exception e) {
            return null;
        }
        if (filepath == null) {
            return null;
        }
        pfp.setFilepath(filepath);
        user.setPfp(pfp);
        userRepository.save(user); 
        return mediaRepository.save(pfp);
    }

    public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

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
            return imageBytes;
        } else {
            return null; // Handle missing images
        }
    }

    // Delete an image
    public String deleteMedia(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Success";
        } else {
            return "Failed"; // Handle missing images
        }
    }
}
