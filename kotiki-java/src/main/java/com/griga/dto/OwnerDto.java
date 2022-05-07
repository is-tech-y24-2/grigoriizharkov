package com.griga.dto;

import com.griga.dao.entities.Owner;

import java.sql.Timestamp;

public class OwnerDto {
    private Long id;
    private String name;
    private Timestamp birthdate;
    private String username;
    private String password;
    private String role;

    public OwnerDto() {

    }

    public OwnerDto(Owner owner) {
        id = owner.getId();
        name = owner.getName();
        birthdate = owner.getBirthdate();
        username = owner.getUsername();
        password = owner.getPassword();
        role = owner.getRole();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
