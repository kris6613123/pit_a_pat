package com.pitapat.pitapatPoint.controller;

import com.pitapat.pitapatPoint.service.CustomerService;
import com.pitapat.pitapatPoint.service.PatService;
import com.pitapat.pitapatPoint.vo.CustomerVO;
import com.pitapat.pitapatPoint.vo.PatVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class CustomerController extends CtrlBase {

    private final CustomerService customerService;
    private final PatService patService;

//    @RequestMapping( value = { "/list" } )
//    public String list (Model model, @RequestParam(defaultValue = "1", required = false) Integer pagenum, @RequestParam(defaultValue = "10", required = false) Integer contentnum, @RequestParam(value="customer", required = false ) String keyowrd ) {
//        customerService.getPagingList( model, pagenum, contentnum, keyowrd );
//        return "customer/list";
//    }
//
    @RequestMapping( value = {"/customer/mod", "/customer/{id}/mod"} )
    public String mod( Model model, @PathVariable( value = "id", required = false ) Integer id  ) {
        if (id == null) {
            model.addAttribute( "customer", new CustomerVO() );
        }
        else {
            CustomerVO customer = customerService.getItemById(id);
            List<PatVO> patList = patService.getListByCustomerId(id);
            model.addAttribute("customer", customer);
            model.addAttribute("patList", patList);
        }
        model.addAttribute("id", id);
        return "customerMod";
    }

    @ResponseBody
    @RequestMapping( value = "/customer/mod/p", method = RequestMethod.POST)
    public ResponseEntity<String> modP( @RequestBody CustomerVO vo ) {
        if ( vo.getCustomer() == null ) {
            customerService.add(vo);
        }
        else {
            customerService.mod(vo);
        }
        String redirectUrl =  "/customer/" + vo.getCustomer() + "/mod";
        return new ResponseEntity<>( redirectUrl, HttpStatus.OK );
    }


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
