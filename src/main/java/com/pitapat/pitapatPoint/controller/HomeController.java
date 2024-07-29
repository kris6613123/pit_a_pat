package com.pitapat.pitapatPoint.controller;

import com.pitapat.pitapatPoint.config.SecurityConfig;
import com.pitapat.pitapatPoint.service.TransTypeService;
import com.pitapat.pitapatPoint.service.UserService;
import com.pitapat.pitapatPoint.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class HomeController extends CtrlBase {

    private final UserService userService;
    private final TransTypeService transTypeService;

    @RequestMapping( value = "/login" )
    public String login (Model model) {
        if( !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser") ) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping( value = "/register" )
    public String register (Model model) {
        //회원가입
        return "register";
    }



    @RequestMapping( value = "/" )
    public String index ( Model model ) {
        model.addAttribute("name", ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername());
        model.addAttribute("transTypeList", transTypeService.getList());
        return "index";
    }
    @RequestMapping( value = "/tables" )
    public String table ( Model model ) {

        return "tables";

    }
}
