package com.griga.service.services;

import com.griga.dao.colors.Color;
import com.griga.dao.entities.Cat;
import com.griga.dao.entities.CatsFriends;
import com.griga.dao.implementations.CatRepository;
import com.griga.dao.implementations.CatsFriendsRepository;
import com.griga.dao.implementations.OwnersCatsRepository;
import com.griga.dto.CatDto;
import com.griga.dto.CatsFriendsDto;
import com.griga.dto.OwnersCatsDto;
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

    public boolean addCat(CatDto catDto) throws ServiceException {
        if (catDto == null) throw new ServiceException("Cat can not be null!");

        cat.save(new Cat(catDto));
        return true;
    }

    public boolean removeCat(Long id) throws ServiceException {
        catsFriends.deleteAllByFirstCatId(id);
        catsFriends.deleteAllBySecondCatId(id);
        ownersCats.deleteAllByCatId(id);
        cat.deleteById(id);

        return true;
    }

    public boolean makeFriendship(CatDto firstCat, Long secondCatId) throws ServiceException {
        if (firstCat == null) throw new ServiceException("Cat can not be null!");

        CatsFriendsDto catsFriendsDto = new CatsFriendsDto();
        catsFriendsDto.setFirstCatId(firstCat.getId());
        catsFriendsDto.setSecondCatId(secondCatId);

        catsFriends.save(new CatsFriends(catsFriendsDto));

        return true;
    }

    public boolean breakFriendship(CatDto firstCat, Long secondCatId) throws ServiceException {
        if (firstCat == null) throw new ServiceException("Cat can not be null!");

        catsFriends.deleteAllByFirstCatIdAndSecondCatId(firstCat.getId(), secondCatId);
        catsFriends.deleteAllByFirstCatIdAndSecondCatId(secondCatId, firstCat.getId());

        return true;
    }

    public List<CatDto> findAllCats() {
        return cat.findAll().stream().map(CatDto::new).toList();
    }

    public List<CatDto> findAllCatsByOwnerId(Long ownerId) {
        List<OwnersCatsDto> catsFriendsDtoList = ownersCats.findAllByOwnerId(ownerId);
        List<CatDto> cats = new ArrayList<>();

        for (OwnersCatsDto ownerCats: catsFriendsDtoList) {
            Optional<CatDto> catDtoOptional = cat.findById(ownerCats.getCatId()).map(CatDto::new);
            catDtoOptional.ifPresent(cats::add);
        }

        return cats;
    }

    public List<CatDto> findCatsByColor(Color color) {
        return cat.findAllByColor(color);
    }

    public List<CatDto> findCatsBySpecies(String species) {
        return cat.findAllBySpecies(species);
    }
}
