package com.griga.dao.entities;

import com.griga.dto.CatsFriendsDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "catsfriends", schema = "public", catalog = "postgres")
public class CatsFriends {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "first_cat_id")
    private Long firstCatId;
    @Basic
    @Column(name = "second_cat_id")
    private Long secondCatId;

    public CatsFriends() {

    }

    public CatsFriends(CatsFriendsDto catsFriendsDto) {
        firstCatId = catsFriendsDto.getFirstCatId();
        secondCatId = catsFriendsDto.getSecondCatId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getFirstCatId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatsFriends that = (CatsFriends) o;
        return Objects.equals(id, that.id) && Objects.equals(firstCatId, that.firstCatId) && Objects.equals(secondCatId, that.secondCatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstCatId, secondCatId);
    }
}
