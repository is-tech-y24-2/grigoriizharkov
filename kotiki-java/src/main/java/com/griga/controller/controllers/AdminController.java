package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dao.colors.Color;
import com.griga.dto.CatDto;
import com.griga.dto.OwnerDto;
import com.griga.service.services.CatService;
import com.griga.service.services.OwnerService;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private CatService catService;

    @GetMapping()
    public String page() {
        return "ADMIN";
    }

    @PostMapping("/add-owner")
    public boolean addOwner(@RequestBody OwnerDto ownerDto) throws ControllerException {
        try {
            return ownerService.addOwner(ownerDto);
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding owner", e);
        }
    }

    @DeleteMapping("/delete-owner/{id}")
    public boolean removeOwner(@PathVariable("id") String ownerId) throws ControllerException {
        try {
            return ownerService.removeOwner(Long.valueOf(ownerId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting owner", e);
        }
    }

    @PostMapping("/add-cat")
    public boolean addCat(@RequestBody CatDto catDto) throws ControllerException {
        try {
            return catService.addCat(catDto);
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat", e);
        }
    }

    @DeleteMapping("/delete-cat/{id}")
    public boolean removeCat(@PathVariable("id") String catId) throws ControllerException {
        try {
            return catService.removeCat(Long.valueOf(catId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting cat", e);
        }
    }

    @PostMapping("/add-cat-to-owner/{id}")
    public boolean addCatToOwner(@RequestBody CatDto catDto, @PathVariable("id") String ownerID)
            throws ControllerException {
        try {
            return ownerService.addCat(catDto, Long.valueOf(ownerID));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat to owner", e);
        }
    }

    @DeleteMapping("/delete-cat-from-owner/{id}")
    public boolean removeCatFromOwner(@RequestBody CatDto catDto, @PathVariable("id") String ownerId)
            throws ControllerException {
        try {
            return ownerService.removeCat(catDto, Long.valueOf(ownerId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting cat from owner", e);
        }
    }

    @PostMapping("/make-friends/{id}")
    public boolean makeFriendship(@RequestBody CatDto firstCatDto, @PathVariable("id") String secondCatId)
            throws ControllerException {
        try {
            return catService.makeFriendship(firstCatDto, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with making friendship", e);
        }
    }

    @DeleteMapping("/break-friends/{id}")
    public boolean breakFriendship(@RequestBody CatDto firstCatDto, @PathVariable("id") String secondCatId)
            throws ControllerException {
        try {
            return catService.breakFriendship(firstCatDto, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with breaking friendship", e);
        }
    }

    @GetMapping("/find-all-owners")
    public List<OwnerDto> findAllOwner(){
        return ownerService.findAllOwners();
    }

    @GetMapping("/find-all-cats")
    public List<CatDto> findAllCats() {
        return catService.findAllCats();
    }

    @GetMapping("find-cats-by-color/{color}")
    public List<CatDto> findAllByColor(@PathVariable("color") String color) {
        try {
            return catService.findCatsByColor(Color.valueOf(color));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("find-cats-by-species/{species}")
    public List<CatDto> findAllBySpecies(@PathVariable("species") String species) {
        return catService.findCatsBySpecies(species);
    }

    @GetMapping("/find-owner-by-username/{username}")
    public OwnerDto findOwnerByUsername(@PathVariable("username") String username) {
        return ownerService.findUserByUsername(username);
    }
}