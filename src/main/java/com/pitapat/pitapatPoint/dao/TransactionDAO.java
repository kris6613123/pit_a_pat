package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.TransactionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionDAO {

    void add( TransactionVO vo );

    List<TransactionVO> getList();

    List<TransactionVO> getAddList();

    List<TransactionVO> getSubList();

}
