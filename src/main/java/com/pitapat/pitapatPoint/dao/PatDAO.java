package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.PatVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatDAO {

    void add(PatVO vo);

    List<PatVO> getListByCustomerId(Integer customer);
}
