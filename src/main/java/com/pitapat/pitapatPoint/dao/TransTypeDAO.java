package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.TransTypeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransTypeDAO {
    void add( TransTypeVO vo );

    void delAll();

    TransTypeVO getItemByType( String type );

    List<TransTypeVO> getList();

    List<TransTypeVO> getRList();
}
