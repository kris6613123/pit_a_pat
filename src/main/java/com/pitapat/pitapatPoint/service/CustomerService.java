package com.pitapat.pitapatPoint.service;


import com.pitapat.pitapatPoint.dao.CustomerDAO;
import com.pitapat.pitapatPoint.vo.CustomerVO;
import com.pitapat.pitapatPoint.vo.TransactionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerDAO customerDAO;

    @Transactional
    public void add( CustomerVO vo ) {
        customerDAO.add(vo);
    }

    @Transactional
    public void mod( CustomerVO vo ) {
        customerDAO.mod(vo);
    }

    @Transactional
    public void addPoint( TransactionVO vo ) {
        customerDAO.addPoint( vo );
    }

    @Transactional
    public void subPoint( TransactionVO vo ) {
        customerDAO.subPoint( vo );
    }

    @Transactional
    public boolean modPoint( TransactionVO vo ) {
        CustomerVO customer = getItemById( vo.getCustomer() );
        if ( vo.getAction() == '-' ) {
            if ( customer.getPoint() - vo.getPoint() < 0 ) {
                return false;
            }
            addPoint( vo );
        }
        else {
            subPoint( vo );
        }
        return true;
    }

    public List<CustomerVO> getSearchList( String keyword ) {
        return customerDAO.getSearchList(keyword);
    }

    public CustomerVO getItemById( Integer customer ) {
        return customerDAO.getItemById(customer);
    }

}
