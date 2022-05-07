package com.griga.dao.implementations;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dto.CatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    void deleteById(Long id);
    List<CatDTO> findAllByColor(Color color);
    List<CatDTO> findAllBySpecies(String species);
}
