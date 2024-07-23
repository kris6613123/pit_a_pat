package com.pitapat.pitapatPoint.service;


import com.pitapat.pitapatPoint.dao.TransTypeDAO;
import com.pitapat.pitapatPoint.vo.TransTypeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransTypeService {
    private final TransTypeDAO transTypeDAO;

    @Transactional
    public void add( TransTypeVO vo ) {
        transTypeDAO.add( vo );
    }

    @Transactional
    public void addAll( List<TransTypeVO> vo ) {
        for (TransTypeVO v : vo) {
            add(v);
        }
    }

    @Transactional
    public void delAll() {
        transTypeDAO.delAll();
    }

    public TransTypeVO getItemByType( String type ) {
        return transTypeDAO.getItemByType( type );
    }

    public List<TransTypeVO> getList() {
        return transTypeDAO.getList();
    }
}
