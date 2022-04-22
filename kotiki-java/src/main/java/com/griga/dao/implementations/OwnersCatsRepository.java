package com.griga.dao.implementations;

import com.griga.dao.entities.OwnersCats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnersCatsRepository extends JpaRepository<OwnersCats, Long> {

}
