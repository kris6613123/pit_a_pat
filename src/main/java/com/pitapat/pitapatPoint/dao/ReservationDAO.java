package com.pitapat.pitapatPoint.dao;

import com.pitapat.pitapatPoint.vo.ReservationVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationDAO {
    void add(ReservationVO vo);
    List<ReservationVO> getList();
    List<ReservationVO> getTodayList();
    List<ReservationVO> getChartList();

    List<ReservationVO> getReuseRateList();
}
