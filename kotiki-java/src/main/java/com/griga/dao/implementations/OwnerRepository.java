package com.griga.dao.implementations;

import com.griga.dao.entities.Owner;
import com.griga.dto.OwnerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    OwnerDTO findByUsername(String username);
    void deleteById(Long id);
}
