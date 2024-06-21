package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Organisation;

public class OrgListDTO {
    public List<Organisation> orgs;

    public OrgListDTO(List<Organisation> orgs) {
        this.orgs = orgs;
    }

    public List<Organisation> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organisation> orgs) {
        this.orgs = orgs;
    }
}