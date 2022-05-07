package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dao.colors.Color;
import com.griga.dto.CatDto;
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
    private final OwnerService ownerService;
    private final CatService catService;

    public OwnerController(OwnerService ownerService, CatService catService) {
        this.ownerService = ownerService;
        this.catService = catService;
    }

    @GetMapping()
    public String page() {
        return "USER";
    }

    @PostMapping("/add-cat")
    public boolean addCat(@RequestBody CatDto catDto, Principal principal) throws ControllerException {
        try {
            return ownerService.addCat(catDto, ownerService.findUserByUsername(principal.getName()).getId());
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding cat to owner", e);
        }
    }

    @DeleteMapping("/delete-cat")
    public boolean removeCat(@RequestBody CatDto catDto, Principal principal) throws ControllerException {
        try {
            return ownerService.removeCat(catDto, ownerService.findUserByUsername(principal.getName()).getId());
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting cat from owner", e);
        }
    }

    @PostMapping("/make-friends/{id}")
    public boolean makeFriendship(@RequestBody CatDto firstCatDto, @PathVariable("id") String secondCatId,
                                  Principal principal) throws ControllerException {
        if (!catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId()).contains(firstCatDto)) {
            throw new ControllerException("Impossible to make friendship between other cats");
        }
        try {
            return catService.makeFriendship(firstCatDto, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with making friendship", e);
        }
    }

    @DeleteMapping("/break-friends/{id}")
    public boolean breakFriendship(@RequestBody CatDto firstCatDto, @PathVariable("id") String secondCatId,
                                   Principal principal) throws ControllerException {
        if (!catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId()).contains(firstCatDto)) {
            throw new ControllerException("Impossible to break friendship between other cats");
        }
        try {
            return catService.breakFriendship(firstCatDto, Long.valueOf(secondCatId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with breaking friendship", e);
        }
    }

    @GetMapping("/find-all-cats")
    public List<CatDto> findAllCats(Principal principal) {
        return catService.findAllCatsByOwnerId(ownerService.findUserByUsername(principal.getName()).getId());
    }

    @GetMapping("/find-cats-by-color/{color}")
    public List<CatDto> findCatsByColor(@PathVariable("color") String color, Principal principal) {
        List<CatDto> catsWithColor = new ArrayList<>();
        List<CatDto> cats = catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId());

        for (CatDto cat: cats) {
            if (Objects.equals(cat.getColor(), Color.valueOf(color))) {
                catsWithColor.add(cat);
            }
        }

        return catsWithColor;
    }

    @GetMapping("/find-cats-by-species/{species}")
    public List<CatDto> findCatsBySpecies(@PathVariable("species") String species, Principal principal) {
        List<CatDto> catsWithSpecies = new ArrayList<>();
        List<CatDto> cats = catService.findAllCatsByOwnerId(
                ownerService.findUserByUsername(principal.getName()).getId());

        for (CatDto cat: cats) {
            if (Objects.equals(cat.getSpecies(), species)) {
                catsWithSpecies.add(cat);
            }
        }

        return catsWithSpecies;
    }
}
