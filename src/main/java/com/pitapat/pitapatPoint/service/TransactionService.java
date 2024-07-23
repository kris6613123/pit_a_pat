package com.pitapat.pitapatPoint.service;


import com.pitapat.pitapatPoint.dao.TransactionDAO;
import com.pitapat.pitapatPoint.vo.TransactionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionDAO transactionDAO;

    public void add( TransactionVO vo ) {
        transactionDAO.add( vo );
    }

    public List<TransactionVO> getList() {
        return transactionDAO.getList();
    }

}
