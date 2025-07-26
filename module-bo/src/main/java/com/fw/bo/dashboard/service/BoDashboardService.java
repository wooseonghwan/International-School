package com.fw.bo.dashboard.service;

import com.fw.core.dto.bo.BoMainDTO;
import com.fw.core.mapper.db1.bo.BoDashboardMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoDashboardService {
    private final BoDashboardMapper boDashboardMapper;
   /* public BoMainDTO selectTodayTaskInfo(BoMainDTO boMainDTO) {
        return boDashboardMapper.selectTodayTaskInfo(boMainDTO);
    }
    public List<BoMainDTO> getInflowStats() {
        return boDashboardMapper.selectInflowStats();
    }
    public List<BoMainDTO> selectDailySales() {
        return boDashboardMapper.selectDailySales();
    }
    public List<BoMainDTO> selectDailyVisitStats() {
        return boDashboardMapper.selectDailyVisitStats();
    }
    public List<BoMainDTO> selectWeeklyVisitStats() {
        return boDashboardMapper.selectWeeklyVisitStats();
    }
    public List<BoMainDTO> selectMonthlyVisitStats() {
        return boDashboardMapper.selectMonthlyVisitStats();
    }
    public List<BoMainDTO> selectProductSales() {
        return boDashboardMapper.selectProductSales();
    }*/
}
