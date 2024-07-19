package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanOrganisationDTO;

public class OrgListDTO {
    public List<CleanOrganisationDTO> orgs;

    public OrgListDTO(List<CleanOrganisationDTO> orgs) {
        this.orgs = orgs;
    }

    public List<CleanOrganisationDTO> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<CleanOrganisationDTO> orgs) {
        this.orgs = orgs;
    }
}