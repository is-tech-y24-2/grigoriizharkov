package com.griga.service.services;

import com.griga.dao.entities.Owner;
import com.griga.dao.entities.OwnersCats;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDTO;
import com.griga.dto.OwnerDTO;
import com.griga.dto.OwnersCatsDTO;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository owner;
    @Autowired
    private OwnersCatsRepository ownersCats;

    public boolean addOwner(OwnerDTO ownerDTO) throws ServiceException {
        if (ownerDTO == null) throw new ServiceException("Owner can not be null!");
        if (Objects.equals(ownerDTO.getRole(), "ROLE_ADMIN")) {
            throw new ServiceException("It is impossible to add admin!");
        }

        owner.save(new Owner(ownerDTO));

        return true;
    }

    public boolean removeOwner(Long id) throws ServiceException {
        ownersCats.deleteAllByOwnerId(id);
        owner.deleteById(id);

        return true;
    }

    public boolean addCat(CatDTO catDTO, Long ownerId) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        List<OwnersCatsDTO> ownersAndCats = ownersCats.findAllByCatIdAndOwnerId(catDTO.getId(), ownerId);
        if (!ownersAndCats.isEmpty()) {
            throw new ServiceException("Such owner-cat pair already exist!");
        }

        OwnersCatsDTO ownersCatsDTO = new OwnersCatsDTO();
        ownersCatsDTO.setOwnerId(ownerId);
        ownersCatsDTO.setCatId(catDTO.getId());

        ownersCats.save(new OwnersCats(ownersCatsDTO));

        return true;
    }

    public boolean removeCat(CatDTO catDTO, Long ownerId) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        ownersCats.deleteAllByCatIdAndOwnerId(catDTO.getId(), ownerId);

        return true;
    }

    public List<OwnerDTO> findAllOwners() {
        return owner.findAll().stream().map(OwnerDTO::new).toList();
    }

    public OwnerDTO findUserByUsername(String username) {
        return owner.findByUsername(username);
    }


}
