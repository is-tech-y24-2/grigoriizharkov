package com.griga.service.services;

import com.griga.dto.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityService implements UserDetailsService {

    private final OwnerService service;

    public SecurityService(OwnerService service) {
        this.service = service;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OwnerDto user = service.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Owner doesn't exist");
        }
        return mapUserToUserDetails(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles){
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private User mapUserToUserDetails(OwnerDto ownerDTO) {
        return new User(ownerDTO.getUsername(), ownerDTO.getPassword(), mapRolesToAuthorities(List.of(ownerDTO.getRole())));
    }
}
