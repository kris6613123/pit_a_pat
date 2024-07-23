package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.CustomerVO;
import com.pitapat.pitapatPoint.vo.TransactionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDAO {
    void add( CustomerVO vo );

    void addPoint( TransactionVO vo );

    void subPoint( TransactionVO vo );

    CustomerVO getItemById( Integer customer );

//    int getCount( @Param("keyword") String keyword );

    List<CustomerVO> getList();

    List<CustomerVO> getSearchList( @Param("keyword") String keyword );


//    List<UserVO> getPagingList( @Param("pagenum") int pagenum, @Param("contentnum") int contentnum, @Param("keyword") String keyword );

}
