package com.pitapat.pitapatPoint.controller;

import com.pitapat.pitapatPoint.config.SecurityConfig;
import com.pitapat.pitapatPoint.service.*;
import com.pitapat.pitapatPoint.vo.ReservationVO;
import com.pitapat.pitapatPoint.vo.TransTypeVO;
import com.pitapat.pitapatPoint.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class HomeController extends CtrlBase {

    private final UserService userService;
    private final TransTypeService transTypeService;
    private final TransactionService transactionService;
    private final ReservationService reservationService;
    private final CustomerService customerService;

    @RequestMapping( value = "/login" )
    public String login (Model model) {
        if( !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser") ) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping( value = "/register" )
    public String register () {
        //회원가입
        return "register";
    }

    @RequestMapping( value = "/setting/mod" )
    public String setting ( Model model ) {
        model.addAttribute("transTypeList", transTypeService.getList());
        return "setting";
    }

    @RequestMapping( value = "/chart/list" )
    public String chart ( Model model ) {
        log.info(customerService.getVipList() + "");
        model.addAttribute("transTypeList", transTypeService.getList());
        model.addAttribute("addList", transactionService.getAddList());
        model.addAttribute("subList", transactionService.getSubList());
        model.addAttribute("vipList", customerService.getVipList());
        model.addAttribute("reservationList", reservationService.getChartList());
        model.addAttribute("reuseRateList", reservationService.getReuseRateList());
        return "chartList";
    }



    @RequestMapping( value = "/" )
    public String index ( Model model ) {
        model.addAttribute("name", ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername());
        model.addAttribute("transTypeList", transTypeService.getList());
        model.addAttribute("addList", transactionService.getAddList());
        model.addAttribute("subList", transactionService.getSubList());
        model.addAttribute("reservationList", reservationService.getChartList());
        return "index";
    }
}
