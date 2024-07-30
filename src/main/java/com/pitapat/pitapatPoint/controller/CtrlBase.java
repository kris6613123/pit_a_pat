package com.pitapat.pitapatPoint.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
public class CtrlBase {

    @ModelAttribute
    public void baseAttribute( Model model ) {
        if( !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser") ) {
            model.addAttribute("name", ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername());
        }
    }
}
