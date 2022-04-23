package com.griga.dto;

import com.griga.dao.entities.Owner;

import java.sql.Timestamp;

public class OwnerDTO {
    private Long id;
    private String name;
    private Timestamp birthdate;

    public OwnerDTO() {

    }

    public OwnerDTO(Owner owner) {
        id = owner.getId();
        name = owner.getName();
        birthdate = owner.getBirthdate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }
}
