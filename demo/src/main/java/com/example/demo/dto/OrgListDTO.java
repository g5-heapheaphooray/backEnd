package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Organization;

public class OrgListDTO {
    public List<Organization> orgs;

    public OrgListDTO(List<Organization> orgs) {
        this.orgs = orgs;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }
}