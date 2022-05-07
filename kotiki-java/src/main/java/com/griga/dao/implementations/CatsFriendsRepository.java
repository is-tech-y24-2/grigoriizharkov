package com.griga.dao.implementations;

import com.griga.dao.entities.CatsFriends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatsFriendsRepository extends JpaRepository<CatsFriends, Long> {
    void deleteAllByFirstCatId(Long firstCatId);
    void deleteAllBySecondCatId(Long secondCatId);
    void deleteAllByFirstCatIdAndSecondCatId(Long firstCatId, Long secondCatId);
}
