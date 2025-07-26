package com.fw.bo.partner.service;

import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoReportDTO;
import com.fw.core.mapper.db1.bo.BoPartnerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoPartnerService {
    private final BoPartnerMapper boPartnerMapper;
    public List<BoPartnerDTO> selectPartnersAdList(BoPartnerDTO boPartnerDTO) {
        return boPartnerMapper.selectPartnersAdList(boPartnerDTO);
    }
    public int selectPartnersAdListCnt(BoPartnerDTO boPartnerDTO) {
        return boPartnerMapper.selectPartnersAdListCnt(boPartnerDTO);
    }
    public BoPartnerDTO selectPartnersAdDetail(String adId) {
        return boPartnerMapper.selectPartnersAdDetail(adId);
    }
    public BoPartnerDTO selectPartnersAdInfo(BoPartnerDTO boPartnerDTO) {
        return boPartnerMapper.selectPartnersAdInfo(boPartnerDTO);
    }
    public BoPartnerDTO selectPartnersAdAmountInfo(BoPartnerDTO boPartnerDTO) {
        return boPartnerMapper.selectPartnersAdAmountInfo(boPartnerDTO);
    }
    public List<BoPartnerDTO> selectPartnersStatusChangeList(BoPartnerDTO boPartnerDTO) {
        return boPartnerMapper.selectPartnersStatusChangeList(boPartnerDTO);
    }
    @Transactional
    public void updatePartnersAdStatus(BoPartnerDTO boPartnerDTO) throws Exception {
        // 기존 status 값 조회
        String currentStatus = boPartnerMapper.selectCurrentStatus(boPartnerDTO.getAdId());
        // 업데이트는 무조건 실행
        boPartnerMapper.updatePartnersAdStatus(boPartnerDTO);
        // status 값이 변경된 경우에만 이력 저장
        if (!boPartnerDTO.getStatus().equals(currentStatus)) {
            boPartnerMapper.insertPartnersStatusHistory(boPartnerDTO);
        }
    }


    public List<BoReportDTO> selectReportList(BoReportDTO boReportDTO) {
        return boPartnerMapper.selectReportList(boReportDTO);
    }
    public int selectReportListCnt(BoReportDTO boReportDTO) {
        return boPartnerMapper.selectReportListCnt(boReportDTO);
    }
}
