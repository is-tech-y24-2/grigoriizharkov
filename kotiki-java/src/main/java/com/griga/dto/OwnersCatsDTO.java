package com.griga.dto;

import com.griga.dao.entities.OwnersCats;

public class OwnersCatsDTO {
    private Long id;
    private Long ownerId;
    private Long catId;

    public OwnersCatsDTO() {

    }

    public OwnersCatsDTO(OwnersCats ownersCats) {
        id = ownersCats.getId();
        ownerId = ownersCats.getOwnerId();
        catId = ownersCats.getCatId();
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
}
