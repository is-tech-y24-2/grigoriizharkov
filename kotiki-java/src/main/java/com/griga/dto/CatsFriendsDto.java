package com.griga.dto;

import com.griga.dao.entities.CatsFriends;

public class CatsFriendsDto {
    private Long id;
    private Long firstCatId;
    private Long secondCatId;

    public CatsFriendsDto() {

    }

    public CatsFriendsDto(CatsFriends catsFriends) {
        id = catsFriends.getId();
        firstCatId = catsFriends.getFirstCatId();
        secondCatId = catsFriends.getSecondCatId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstCatId() {
        return firstCatId;
    }

    public void setFirstCatId(Long firstCatId) {
        this.firstCatId = firstCatId;
    }

    public Long getSecondCatId() {
        return secondCatId;
    }

    public void setSecondCatId(Long secondCatId) {
        this.secondCatId = secondCatId;
    }
}
