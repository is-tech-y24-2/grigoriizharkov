package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dao.colors.Color;
import com.griga.dto.CatDTO;
import com.griga.service.services.CatService;
import com.griga.service.services.OwnerService;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private CatService catService;

    @GetMapping()
    public String page() {
        return "USER";
    }

    @PostMapping("/add-cat")
    public boolean addCat(@RequestBody CatDTO catDTO, Principal principal) throws ControllerException {
        try {
            return ownerService.addCat(catDTO, ownerService.findUserByUsername(principal.getName()).getId());
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat to owner", e);
        }
    }

    @DeleteMapping("/delete-cat")
    public boolean removeCat(@RequestBody CatDTO catDTO, Principal principal) throws ControllerException {
        try {
            return ownerService.removeCat(catDTO, ownerService.findUserByUsername(principal.getName()).getId());
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting cat from owner", e);
        }
    }

    @PostMapping("/make-friends/{id}")
    public boolean makeFriendship(@RequestBody CatDTO firstCatDTO, @PathVariable("id") String secondCatId,
                                  Principal principal) throws ControllerException {
        if (!catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId()).contains(firstCatDTO)) {
            throw new ControllerException("Impossible to make friendship between other cats");
        }
        try {
            return catService.makeFriendship(firstCatDTO, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with making friendship", e);
        }
    }

    @DeleteMapping("/break-friends/{id}")
    public boolean breakFriendship(@RequestBody CatDTO firstCatDTO, @PathVariable("id") String secondCatId,
                                   Principal principal) throws ControllerException {
        if (!catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId()).contains(firstCatDTO)) {
            throw new ControllerException("Impossible to break friendship between other cats");
        }
        try {
            return catService.breakFriendship(firstCatDTO, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with breaking friendship", e);
        }
    }

    @GetMapping("/find-all-cats")
    public List<CatDTO> findAllCats(Principal principal) {
        return catService.findAllCatsByOwnerId(ownerService.findUserByUsername(principal.getName()).getId());
    }

    @GetMapping("/find-cats-by-color/{color}")
    public List<CatDTO> findCatsByColor(@PathVariable("color") String color, Principal principal) {
        List<CatDTO> catsWithColor = new ArrayList<>();
        List<CatDTO> cats = catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId());

        for (CatDTO cat: cats) {
            if (Objects.equals(cat.getColor(), Color.valueOf(color))) {
                catsWithColor.add(cat);
            }
        }

        return catsWithColor;
    }

    @GetMapping("/find-cats-by-species/{species}")
    public List<CatDTO> findCatsBySpecies(@PathVariable("species") String species, Principal principal) {
        List<CatDTO> catsWithSpecies = new ArrayList<>();
        List<CatDTO> cats = catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId());

        for (CatDTO cat: cats) {
            if (Objects.equals(cat.getSpecies(), species)) {
                catsWithSpecies.add(cat);
            }
        }

        return catsWithSpecies;
    }
}
