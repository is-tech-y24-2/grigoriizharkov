package com.griga.service.services;

import com.griga.dao.entities.Owner;
import com.griga.dao.entities.OwnersCats;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDto;
import com.griga.dto.OwnerDto;
import com.griga.dto.OwnersCatsDto;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OwnerService {
    private final OwnerRepository owner;
    private final OwnersCatsRepository ownersCats;

    public OwnerService(OwnerRepository owner, OwnersCatsRepository ownersCats) {
        this.owner = owner;
        this.ownersCats = ownersCats;
    }

    public boolean addOwner(OwnerDto ownerDto) throws ServiceException {
        if (ownerDto == null) throw new ServiceException("Owner can not be null!");
        if (Objects.equals(ownerDto.getRole(), "ROLE_ADMIN")) {
            throw new ServiceException("It is impossible to add admin!");
        }

        owner.save(new Owner(ownerDto));

        return true;
    }

    public boolean removeOwner(Long id) throws ServiceException {
        ownersCats.deleteAllByOwnerId(id);
        owner.deleteById(id);

        return true;
    }

    public boolean addCat(CatDto catDto, Long ownerId) throws ServiceException {
        if (catDto == null) throw new ServiceException("Cat can not be null!");

        List<OwnersCatsDto> ownersAndCats = ownersCats.findAllByCatIdAndOwnerId(catDto.getId(), ownerId);
        if (!ownersAndCats.isEmpty()) {
            throw new ServiceException("Such owner-cat pair already exist!");
        }

        OwnersCatsDto ownersCatsDTO = new OwnersCatsDto();
        ownersCatsDTO.setOwnerId(ownerId);
        ownersCatsDTO.setCatId(catDto.getId());

        ownersCats.save(new OwnersCats(ownersCatsDTO));

        return true;
    }

    public boolean removeCat(CatDto catDTO, Long ownerId) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        ownersCats.deleteAllByCatIdAndOwnerId(catDTO.getId(), ownerId);

        return true;
    }

    public List<OwnerDto> findAllOwners() {
        return owner.findAll().stream().map(OwnerDto::new).toList();
    }

    public OwnerDto findUserByUsername(String username) {
        return owner.findByUsername(username);
    }


}
