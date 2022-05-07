package com.griga.service.services;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dao.entities.CatsFriends;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDTO;
import com.griga.dto.CatsFriendsDTO;
import com.griga.dto.OwnersCatsDTO;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    @Autowired
    private CatRepository cat;
    @Autowired
    private CatsFriendsRepository catsFriends;
    @Autowired
    private OwnersCatsRepository ownersCats;

    public boolean addCat(CatDTO catDTO) throws ServiceException {
        if (catDTO == null) throw new ServiceException("Cat can not be null!");

        cat.save(new Cat(catDTO));
        return true;
    }

    public boolean removeCat(Long id) throws ServiceException {
        catsFriends.deleteAllByFirstCatId(id);
        catsFriends.deleteAllBySecondCatId(id);
        ownersCats.deleteAllByCatId(id);
        cat.deleteById(id);

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

        catsFriends.deleteAllByFirstCatIdAndSecondCatId(firstCat.getId(), secondCatId);
        catsFriends.deleteAllByFirstCatIdAndSecondCatId(secondCatId, firstCat.getId());

        return true;
    }

    public List<CatDTO> findAllCats() {
        return cat.findAll().stream().map(CatDTO::new).toList();
    }

    public List<CatDTO> findAllCatsByOwnerId(Long ownerId) {
        List<OwnersCatsDTO> catsFriendsDTOList = ownersCats.findAllByOwnerId(ownerId);
        List<CatDTO> cats = new ArrayList<>();

        for (OwnersCatsDTO ownerCats: catsFriendsDTOList) {
            Optional<CatDTO> catDTOOptional = cat.findById(ownerCats.getCatId()).map(CatDTO::new);
            catDTOOptional.ifPresent(cats::add);
        }

        return cats;
    }

    public List<CatDTO> findCatsByColor(Color color) {
        return cat.findAllByColor(color);
    }

    public List<CatDTO> findCatsBySpecies(String species) {
        return cat.findAllBySpecies(species);
    }
}
