package com.pitapat.pitapatPoint.controller;

import com.pitapat.pitapatPoint.service.CustomerService;
import com.pitapat.pitapatPoint.service.UserService;
import com.pitapat.pitapatPoint.vo.CustomerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class CustomerController {

    private final CustomerService customerService;

//    @RequestMapping( value = { "/list" } )
//    public String list (Model model, @RequestParam(defaultValue = "1", required = false) Integer pagenum, @RequestParam(defaultValue = "10", required = false) Integer contentnum, @RequestParam(value="customer", required = false ) String keyowrd ) {
//        customerService.getPagingList( model, pagenum, contentnum, keyowrd );
//        return "customer/list";
//    }
//
//    @RequestMapping( value = { "/mod", "/{id}/mod" } )
//    public String mod( Model model, @PathVariable( value = "id", required = false ) Integer id ) {
//        CustomerVO customer = new CustomerVO();
//        List<BranchVO> branchList = branchService.getList();
//        if ( id != null ) {
//            customer = customerService.getItem( new CustomerVO( id ) );
//        }
//        model.addAttribute( "customer", customer );
//        model.addAttribute( "branchList", branchList );
//
//        return "customer/mod";
//    }

//
//    @RequestMapping( value = {"/{id}/del"}, method = RequestMethod.POST )
//    public ResponseEntity<String> del( @PathVariable( value = "id" ) Integer id ) {
//        customerService.delete( new CustomerVO( id ) );
//        gameService.delByCustomer( id );
//        return new ResponseEntity<>( "성공적으로 삭제되었습니다.", HttpStatus.OK );
//    }


//    @RequestMapping( value = { "/{id}/view" } )
//    public String view( Model model, @PathVariable( value = "id" ) Integer id, @RequestParam( value="game", required = false ) String keyowrd ) {
//        model.addAttribute( "tier", stdTierService.getItemByCustomer( id ) );
//        model.addAttribute( "customer", customerService.getItem( new CustomerVO( id ) ) );
//        model.addAttribute( "ranking", rankingHistoryService.getItemByCustomer( id ) );
//        model.addAttribute( "gameList", gameService.getListByCustomer( id, keyowrd ) );
//        return "customer/view";
//    }

    @ResponseBody
    @RequestMapping( value = {"/customer/{keyword}/list"}, method = RequestMethod.POST  )
    public ResponseEntity<List<CustomerVO>> keywordList(@PathVariable( value = "keyword" ) String keyword ) {
        return new ResponseEntity<>( customerService.getSearchList( keyword ), HttpStatus.OK );
    }

}
