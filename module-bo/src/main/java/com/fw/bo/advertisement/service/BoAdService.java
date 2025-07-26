package com.fw.bo.advertisement.service;

import com.fw.core.dto.bo.BoAdDTO;
import com.fw.core.dto.bo.BoEstimateDTO;
import com.fw.core.mapper.db1.bo.BoAdMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoAdService {
    private final BoAdMapper boAdMapper;

    public List<BoAdDTO> selectAdHistoryList(BoAdDTO boAdDTO) {
        return boAdMapper.selectAdHistoryList(boAdDTO);
    }
    public BoAdDTO selectAdHistoryDetail(BoAdDTO boAdDTO) {
        return boAdMapper.selectAdHistoryDetail(boAdDTO);
    }
    public int selectAdHistoryListCnt(BoAdDTO boAdDTO) {
        return boAdMapper.selectAdHistoryListCnt(boAdDTO);
    }
    @Transactional
    public void updateAdHistory(BoAdDTO boAdDTO) throws Exception {
        if ("AD-PROGRESS".equals(boAdDTO.getStatus())) {
            boAdDTO.setStartDt("TRIGGER");
        }
        boAdMapper.updateAdHistory(boAdDTO);
        if ("SUCCESS".equals(boAdDTO.getStatus())) {
        boAdMapper.insertAdPayment(boAdDTO);
        }
    }
}
