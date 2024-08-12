package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDAO {
    void add(FileVO vo);

    void del(FileVO vo);

    FileVO getItemById( Integer file );

//    List<FileVO> getList();

}
