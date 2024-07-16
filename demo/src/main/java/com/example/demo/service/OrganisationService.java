package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.RegisterOrganisationDTO;
import com.example.demo.dto.models.CleanOrganisationDTO;
import com.example.demo.model.Media;
import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.example.demo.repository.OrganisationRepository;

@Service
public class OrganisationService {
    
    private final OrganisationRepository organisationRepository;
    private final MediaService mediaService;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, MediaService mediaService) {
        this.organisationRepository = organisationRepository;
        this.mediaService = mediaService;
    }

//    public Organisation createOrganisation(Organisation organisation) {
//        return organisationRepository.save(organisation);
//    }

    public CleanOrganisationDTO getCleanOrg(Organisation o) {
        return new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), mediaService.getObjectUrl(o.getPfp().getFilepath()));
    }

    public CleanOrganisationDTO getCleanOrg(String email) {
        Organisation o = organisationRepository.findById(email).orElse(null);
        if (o == null) {
            return null;
        }
        return new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), mediaService.getObjectUrl(o.getPfp().getFilepath()));
    }

    public Organisation updateVerified(String id) {
        Organisation o = organisationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);

        return organisationRepository.save(o);
    }

    public Organisation updateDetails(RegisterOrganisationDTO dto, User user) {
        if (user instanceof Organisation o) {
            o.setContactNo(dto.getContactNo());
            o.setFullName(dto.getFullName());
            o.setLocation(dto.getLocation());
            o.setWebsite(dto.getWebsite());
            o.setDescription(dto.getDescription());
            
            return organisationRepository.save(o);
        }
        return null;
    }

    public Organisation getOrg(String email) {
        return organisationRepository.findById(email).orElse(null);
    }

    public List<CleanOrganisationDTO> getAllOrg() {
        List<Organisation> orgs = organisationRepository.findAll();
        List<CleanOrganisationDTO> cleanOrgs = new ArrayList<>();
        for (Organisation o : orgs) {
            if (o.isVerified() && o.isAccountNonLocked()) {
                cleanOrgs.add(getCleanOrg(o));
            }
        }
        return cleanOrgs;
    }
}