package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dto.OwnerDTO;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.griga.service.SpringService;

import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private SpringService service;

    @GetMapping("/get-all")
    public List<OwnerDTO> getAllOwner(){
        return service.getAllOwners();
    }

    @PostMapping("/add")
    public boolean addOwner(@RequestBody OwnerDTO ownerDTO) throws ControllerException {
        try {
            return service.addOwner(ownerDTO);
        } catch (ServiceException e) {
            throw new ControllerException("Problem with adding owner", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public boolean removeOwner(@PathVariable("id") String ownerId) throws ControllerException {
        try {
            return service.removeOwner(Long.valueOf(ownerId));
        } catch (ServiceException e) {
            throw new ControllerException("Problem with deleting owner", e);
        }
    }
}