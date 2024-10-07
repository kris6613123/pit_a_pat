package com.pitapat.pitapatPoint.controller;

import com.pitapat.pitapatPoint.service.CustomerService;
import com.pitapat.pitapatPoint.service.TransTypeService;
import com.pitapat.pitapatPoint.service.TransactionService;
import com.pitapat.pitapatPoint.vo.TransTypeVO;
import com.pitapat.pitapatPoint.vo.TransactionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class TransactionController extends CtrlBase{

    private final TransactionService transactionService;
    private final TransTypeService transTypeService;
    private final CustomerService customerService;

    @RequestMapping( value = { "/transaction/list" } )
    public String list ( Model model ) {
        model.addAttribute("transactionList", transactionService.getList());
        return "transactionList";
    }

    @ResponseBody
    @RequestMapping( value = {"/transaction/mod"}, method = RequestMethod.POST )
    public ResponseEntity<String> mod( @RequestBody TransactionVO vo ) {
        log.info("TransactionVO  : " + vo);
        if( vo.getPoint() == null ) {
            TransTypeVO transType = transTypeService.getItemByType( vo.getType() );
            vo.setPoint( (int) ( vo.getPrice() * transType.getRate() ) );
        }
        if( customerService.modPoint( vo ) == false ) {
            return new ResponseEntity<>( "포인트가 부족합니다.", HttpStatus.BAD_REQUEST );
        }
        transactionService.add( vo );

        return new ResponseEntity<>( "성공적으로 등록되었습니다.", HttpStatus.OK );
    }


}
