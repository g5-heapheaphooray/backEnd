package com.example.demo.service;

import java.util.Map;

import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organization;
import com.example.demo.repository.OrganizationRepository;

@Service
public class OrganizationService {
    
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

//    public Organization createOrganization(Organization organization) {
//        return organizationRepository.save(organization);
//    }

    public Organization updateVerified(String id) {
        Organization o = organizationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);

        return organizationRepository.save(o);
    }

    public Organization updateDetails(String id, Map<String, String> payload) {
        Organization o = organizationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setContactNo(payload.get("contactNo"));
        o.setFullName(payload.get("fullName"));
        o.setLocation(payload.get("location"));
        o.setWebsite(payload.get("website"));
        o.setDescription(payload.get("description"));
        return organizationRepository.save(o);
    }

    public Organization getOrg(String email) {
        Organization o = organizationRepository.findById(email).orElse(null);
        return o;
    }

}