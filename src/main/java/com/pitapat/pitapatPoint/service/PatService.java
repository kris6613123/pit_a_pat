package com.pitapat.pitapatPoint.service;

import com.pitapat.pitapatPoint.dao.PatDAO;
import com.pitapat.pitapatPoint.vo.PatVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatService {
    private final PatDAO patDAO;

    public void add( PatVO vo ) {
        patDAO.add( vo );
    }

    public List<PatVO> getListByCustomerId( Integer customer ) {
        return patDAO.getListByCustomerId( customer );
    }
}
