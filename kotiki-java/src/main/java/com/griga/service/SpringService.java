package com.griga.service;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dao.entities.CatsFriends;
import com.griga.dao.entities.Owner;
import com.griga.dao.entities.OwnersCats;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnerRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDTO;
import com.griga.dto.CatsFriendsDTO;
import com.griga.dto.OwnerDTO;
import com.griga.dto.OwnersCatsDTO;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SpringService {

    @Autowired
    private OwnerRepository owner;
    @Autowired
    private CatRepository cat;
    @Autowired
    private OwnersCatsRepository ownersCats;
    @Autowired
    private CatsFriendsRepository catsFriends;

    public boolean addCat(CatDTO catDTO) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        cat.save(new Cat(catDTO));
        return true;
    }

    public boolean removeCat(Long catId) throws ServiceException {
        List<CatsFriends> catsAndFriends = catsFriends.findAll();
        for (CatsFriends catAndFriend: catsAndFriends) {
            if (catAndFriend.getFirstCatId() == catId || Objects.equals(catAndFriend.getSecondCatId(), catId)) {
                catsFriends.delete(catsFriends.getById(catAndFriend.getId()));
            }
        }

        List<OwnersCats> ownersAndCats = ownersCats.findAll();
        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (Objects.equals(ownerAndCat.getCatId(), catId)) {
                ownersCats.delete(ownersCats.getById(ownerAndCat.getId()));
            }
        }

        cat.delete(cat.getById(catId));

        return true;
    }

    public boolean addOwner(OwnerDTO ownerDTO) throws ServiceException {
        if (ownerDTO == null) throw new ServiceException("Owner can not be null!");

        owner.save(new Owner(ownerDTO));
        return true;
    }

    public boolean removeOwner(Long ownerId) throws ServiceException {
        List<OwnersCats> ownersAndCats = ownersCats.findAll();
        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (Objects.equals(ownerAndCat.getOwnerId(), ownerId)) {
                ownersCats.delete(ownersCats.getById(ownerAndCat.getId()));
            }
        }

        owner.delete(owner.getById(ownerId));

        return true;
    }

    public boolean addCatToOwner(CatDTO catDTO, Long ownerId) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        List<OwnersCats> ownersAndCats = ownersCats.findAll();
        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (Objects.equals(ownerAndCat.getOwnerId(), ownerId) && Objects.equals(ownerAndCat.getCatId(), catDTO.getId())) {
                throw new ServiceException("Such owner-cat pair already exist!");
            }
        }

        OwnersCatsDTO ownersCatsDTO = new OwnersCatsDTO();
        ownersCatsDTO.setOwnerId(ownerId);
        ownersCatsDTO.setCatId(catDTO.getId());

        ownersCats.save(new OwnersCats(ownersCatsDTO));

        return true;
    }

    public boolean removeCatFromOwner(CatDTO catDTO, Long ownerId) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        List<OwnersCats> ownersAndCats = ownersCats.findAll();
        for (OwnersCats ownerAndCat: ownersAndCats) {
            if (Objects.equals(ownerAndCat.getOwnerId(), ownerId) && Objects.equals(ownerAndCat.getCatId(), catDTO.getId())) {
                ownersCats.delete(ownersCats.getById(ownerAndCat.getId()));
            }
        }

        return true;
    }

    public boolean makeFriendship(CatDTO firstCat, Long secondCatId) throws ServiceException {
        if (firstCat == null) throw new ServiceException("Cat can not be null!");

        CatsFriendsDTO catsFriendsDTO = new CatsFriendsDTO();
        catsFriendsDTO.setFirstCatId(firstCat.getId());
        catsFriendsDTO.setSecondCatId(secondCatId);

        catsFriends.save(new CatsFriends(catsFriendsDTO));

        return true;
    }

    public boolean breakFriendship(CatDTO firstCat, Long secondCatId) throws ServiceException {
        if (firstCat == null) throw new ServiceException("Cat can not be null!");

        List<CatsFriends> catsAndFriends = catsFriends.findAll();
        for (CatsFriends catAndFriend: catsAndFriends) {
            if (catAndFriend.getFirstCatId() == firstCat.getId() && Objects.equals(catAndFriend.getSecondCatId(), secondCatId) ||
                    catAndFriend.getFirstCatId() == secondCatId && Objects.equals(catAndFriend.getSecondCatId(), firstCat.getId())) {
                catsFriends.delete(catAndFriend);
            }
        }

        return true;
    }

    public List<CatDTO> getCatsByColor(Color color) {
        return getAllCats().stream().filter(cat -> Objects.equals(cat.getColor(), color)).toList();
    }

    public List<CatDTO> getAllCats() {
        return cat.findAll().stream().map(CatDTO::new).toList();
    }

    public List<OwnerDTO> getAllOwners() {
        return owner.findAll().stream().map(OwnerDTO::new).toList();
    }

}
