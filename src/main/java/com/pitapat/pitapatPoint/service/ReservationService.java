package com.pitapat.pitapatPoint.service;

import com.pitapat.pitapatPoint.dao.ReservationDAO;
import com.pitapat.pitapatPoint.vo.PatVO;
import com.pitapat.pitapatPoint.vo.ReservationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public void add( ReservationVO vo ) {
        reservationDAO.add( vo );
    }

    public List<ReservationVO> getList() {
        return reservationDAO.getList();
    }

    public List<ReservationVO> getTodayList() {
        return reservationDAO.getTodayList();
    }
    public List<ReservationVO> getChartList() {
        return reservationDAO.getChartList();
    }

    public List<ReservationVO> getReuseRateList() {
        return reservationDAO.getReuseRateList();
    }
}
