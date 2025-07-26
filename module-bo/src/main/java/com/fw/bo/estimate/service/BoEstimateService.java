package com.fw.bo.estimate.service;

import com.fw.core.dto.bo.BoEstimateDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.mapper.db1.bo.BoEstimateMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@AllArgsConstructor
public class BoEstimateService {
    private final BoEstimateMapper boEstimateMapper;
    public List<BoEstimateDTO> selectEstimateList(BoEstimateDTO boEstimateDTO) {
        return boEstimateMapper.selectEstimateList(boEstimateDTO);
    }
    public int selectEstimateListCnt(BoEstimateDTO boEstimateDTO) {
        return boEstimateMapper.selectEstimateListCnt(boEstimateDTO);
    }
    public BoEstimateDTO selectEstimateInfo(String estimateId) {
        return boEstimateMapper.selectEstimateInfo(estimateId);
    }
    @Transactional
    public void updateEstimate(BoEstimateDTO boEstimateDTO) throws Exception {
        boEstimateMapper.updateEstimate(boEstimateDTO);
        boEstimateMapper.insertEstimateHistory(boEstimateDTO);

        if ("SUCCESS".equals(boEstimateDTO.getStatus())) {
            boEstimateMapper.insertPayment(boEstimateDTO);
        }
    }

    public List<String> selectEstimateHistoryList(String estimateId) {
        return boEstimateMapper.selectEstimateHistoryList(estimateId);
    }
    public BoEstimateDTO selectEstimateHistoryInfo(String estimateId, String createDt) {
        BoEstimateDTO param = new BoEstimateDTO();
        param.setEstimateId(estimateId);
        param.setCreateDt(createDt);
        return boEstimateMapper.selectEstimateHistoryInfo(param);
    }
}
