package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organisation;
import com.example.demo.repository.OrganisationRepository;

@Service
public class OrganisationService {
    
    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

//    public Organisation createOrganisation(Organisation organisation) {
//        return organisationRepository.save(organisation);
//    }

    public Organisation updateVerified(String id) {
        Organisation o = organisationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);

        return organisationRepository.save(o);
    }

    public Organisation updateDetails(String id, Map<String, String> payload) {
        Organisation o = organisationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setContactNo(payload.get("contactNo"));
        o.setFullName(payload.get("fullName"));
        o.setLocation(payload.get("location"));
        o.setWebsite(payload.get("website"));
        o.setDescription(payload.get("description"));
        return organisationRepository.save(o);
    }

    public Organisation getOrg(String email) {
        return organisationRepository.findById(email).orElse(null);
    }

    public List<Organisation> getAllOrg() {
        return organisationRepository.findAll();
    }
}