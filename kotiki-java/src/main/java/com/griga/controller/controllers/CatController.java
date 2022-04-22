package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dao.colors.Color;
import com.griga.dto.CatDTO;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.griga.service.SpringService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cat")
public class CatController {
    @Autowired
    private SpringService service;

    @GetMapping("/getall")
    public List<CatDTO> getAllCats() {
        return service.getAllCats();
    }

    @PostMapping("/add")
    public boolean addCat(@RequestBody CatDTO catDTO) throws ControllerException {
        try {
            return service.addCat(catDTO);
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeCat(@PathVariable("id") String catId) throws ControllerException {
        try {
            return service.removeCat(Long.valueOf(catId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting cat", e);

        }
    }

    @PostMapping("/addtoowner/{id}")
    public boolean addToOwner(@RequestBody CatDTO catDTO, @PathVariable("id") String ownerId) throws ControllerException {
        try {
            return service.addCatToOwner(catDTO, Long.valueOf(ownerId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat to owner", e);
        }
    }

    @DeleteMapping("/removefromowner/{id}")
    public boolean removeFromOwner(@RequestBody CatDTO catDTO, @PathVariable("id") String ownerId) throws ControllerException {
        try {
            return service.removeCatFromOwner(catDTO, Long.valueOf(ownerId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with removing cat from owner", e);
        }
    }

    @GetMapping("getbycolor/{color}")
    public List<CatDTO> getByColor(@PathVariable("color") String color) {
        try {
            return service.getCatsByColor(Color.valueOf(color));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/makefriends/{id}")
    public boolean makeFriendship(@RequestBody CatDTO firstCatDTO, @PathVariable("id") String secondCatId) throws ControllerException {
        try {
            return service.makeFriendship(firstCatDTO, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with making friendship", e);
        }
    }

    @DeleteMapping("/breakfriends/{id}")
    public boolean breakFriendship(@RequestBody CatDTO firstCatDTO, @PathVariable("id") String secondCatId) throws ControllerException {
        try {
            return service.breakFriendship(firstCatDTO, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with breaking friendship", e);
        }
    }

}
