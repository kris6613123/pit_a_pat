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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping( method = RequestMethod.GET )
public class CustomerController extends CtrlBase {

    private final CustomerService customerService;
    private final PatService patService;

    @RequestMapping( value = { "/customer/list" } )
    public String list ( Model model ) {
        model.addAttribute("customerList", customerService.getList());
        return "customerList";
    }

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

//    @ResponseBody
    @RequestMapping( value = "/customer/mod/p", method = RequestMethod.POST)
    public ResponseEntity<String> modP( @RequestPart( "vo" ) CustomerVO vo, @RequestPart( "patList" )  List<PatVO> patList ) {

        if ( vo.getCustomer() == null ) {
            customerService.add(vo);
        }
        else {
            customerService.mod(vo);
        }
        for ( PatVO pat : patList ) {
            if ( pat.getPat() == null ) {
                pat.setCustomer( vo.getCustomer() );
                patService.add( pat );
            }
        }
        String redirectUrl =  "/customer/" + vo.getCustomer() + "/mod";
        return new ResponseEntity<>( redirectUrl, HttpStatus.OK );
    }


    @ResponseBody
    @RequestMapping( value = {"/customer/{keyword}/list"}, method = RequestMethod.POST  )
    public ResponseEntity<List<CustomerVO>> keywordList(@PathVariable( value = "keyword" ) String keyword ) {
        return new ResponseEntity<>( customerService.getSearchList( keyword ), HttpStatus.OK );
    }

    @ResponseBody
    @RequestMapping( value = {"/pat/{customer}/list"}, method = RequestMethod.POST  )
    public ResponseEntity<List<PatVO>> patList(@PathVariable( value = "customer" ) String customer ) {
        return new ResponseEntity<>( patService.getListByCustomerId( Integer.parseInt(customer) ), HttpStatus.OK );
    }

}
