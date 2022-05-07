package com.griga.controller.controllers;

import com.griga.controller.tools.ControllerException;
import com.griga.dto.OwnerDto;
import com.griga.service.services.OwnerService;
import com.griga.service.tools.ServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final OwnerService service;

    public MainController(OwnerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(){
        return "WELCOME TO THE HOMEPAGE";
    }

    @PostMapping("/registration")
    public void registration(@RequestBody OwnerDto owner) throws ControllerException {
        try {
            service.addOwner(owner);
        } catch (ServiceException e) {
            throw new ControllerException("Problems with user registration", e);
        }
    }
}