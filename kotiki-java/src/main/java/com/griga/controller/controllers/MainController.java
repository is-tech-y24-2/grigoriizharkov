package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dto.OwnerDTO;
import com.griga.service.services.OwnerService;
import com.griga.service.tools.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("mainController")
public class MainController {
    @Autowired
    private OwnerService service;

    @GetMapping("/")
    public String home(){
        return "WELCOME TO THE HOMEPAGE";
    }

    @PostMapping("/registration")
    public void registration(@RequestBody OwnerDTO owner) throws ControllerException {
        try {
            service.addOwner(owner);
        } catch (ServiceException e) {
            throw new ControllerException("Problems with user registration", e);
        }
    }
}