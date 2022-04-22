package com.griga.dao.implementations;

import com.griga.dao.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
