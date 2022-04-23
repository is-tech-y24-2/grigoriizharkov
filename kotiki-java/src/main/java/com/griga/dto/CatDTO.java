package com.griga.dto;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;

import java.sql.Timestamp;

public class CatDTO {
    private Long id;
    private String name;
    private Timestamp birthdate;
    private String species;
    private Color color;

    public CatDTO() {

    }

    public CatDTO(Cat cat) {
        id = cat.getId();
        name = cat.getName();
        birthdate = cat.getBirthdate();
        species = cat.getSpecies();
        color = cat.getColor();
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
