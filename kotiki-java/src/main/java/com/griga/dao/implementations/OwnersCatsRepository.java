package com.griga.dao.implementations;

import com.griga.dao.entities.OwnersCats;
import com.griga.dto.OwnersCatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnersCatsRepository extends JpaRepository<OwnersCats, Long> {
    void deleteAllByCatId(Long catId);
    void deleteAllByOwnerId(Long ownerId);
    void deleteAllByCatIdAndOwnerId(Long catId, Long ownerId);
    List<OwnersCatsDto> findAllByCatIdAndOwnerId(Long catId, Long ownerId);
    List<OwnersCatsDto> findAllByOwnerId(Long ownerId);
}
