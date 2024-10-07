package com.pitapat.pitapatPoint.controller;


import com.pitapat.pitapatPoint.service.ReservationService;
import com.pitapat.pitapatPoint.service.TransTypeService;
import com.pitapat.pitapatPoint.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class ReservationController extends CtrlBase {

    private final ReservationService reservationService;
    private final TransTypeService transTypeService;

    @RequestMapping( value = { "/reservation" } )
    public String todayList ( Model model ) {
        List<ReservationVO> reservationList = reservationService.getTodayList();
        List<TransTypeVO> transTypeList = transTypeService.getRList();
        for (TransTypeVO transType : transTypeList) {
            // transType의 type 필드에 따라 num을 증가
            for (ReservationVO reservation : reservationList) {
                if (transType.getType().equals(reservation.getType())) {
                    transType.setNum(transType.getNum() + 1);
                    break; // 해당 type을 찾았으므로 더 이상 반복할 필요 없음
                }
            }
        }

        model.addAttribute("reservationList", reservationList);
        model.addAttribute("name", ( (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername());
        model.addAttribute("transTypeList", transTypeList);
        return "reservation";
    }

    @RequestMapping( value = { "/reservation/list" } )
    public String list ( Model model ) {
        model.addAttribute("reservationList", reservationService.getList() );
        return "reservationList";
    }

    @ResponseBody
    @RequestMapping( value = "/reservation/mod", method = RequestMethod.POST)
    public ResponseEntity<String> mod(@RequestBody ReservationVO vo ) {
        log.info("ReservationVO vo : " + vo);
        reservationService.add( vo );
        return new ResponseEntity<>( "성공적으로 등록되었습니다.", HttpStatus.OK );
    }

}
