package com.griga.dao.entities;

import com.griga.dto.OwnersCatsDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ownerscats", schema = "public", catalog = "postgres")
public class OwnersCats {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "owner_id")
    private Long ownerId;
    @Basic
    @Column(name = "cat_id")
    private Long catId;

    public OwnersCats() {

    }

    public OwnersCats(OwnersCatsDto ownersCatsDto) {
        ownerId = ownersCatsDto.getOwnerId();
        catId = ownersCatsDto.getCatId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnersCats that = (OwnersCats) o;
        return Objects.equals(id, that.id) && Objects.equals(ownerId, that.ownerId) && Objects.equals(catId, that.catId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, catId);
    }
}
