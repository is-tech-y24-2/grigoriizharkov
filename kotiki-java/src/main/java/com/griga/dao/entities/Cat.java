package com.griga.dao.entities;

import com.griga.dao.colors.Color;
import com.griga.dto.CatDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cats", schema = "public", catalog = "postgres")
public class Cat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "birthdate")
    private Timestamp birthdate;
    @Basic
    @Column(name = "species")
    private String species;
    @Basic
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    public Cat() {
    }

    public Cat(CatDto catDto) {
        name = catDto.getName();
        birthdate = catDto.getBirthdate();
        species = catDto.getSpecies();
        color = catDto.getColor();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat that = (Cat) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(birthdate, that.birthdate) && Objects.equals(species, that.species) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, species, color);
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + birthdate + ", " + species + ", " + color;
    }
}
