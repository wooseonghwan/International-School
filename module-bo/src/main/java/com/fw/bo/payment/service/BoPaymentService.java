package com.fw.bo.payment.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.mapper.db1.bo.BoPaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoPaymentService {
    private final BoPaymentMapper boPaymentMapper;
 /*   private final CommonFileService commonFileService;*/

    // 결제 리스트
    public List<BoPaymentDTO> selectPaymentList(BoPaymentDTO boPaymentDTO) {
        return boPaymentMapper.selectPaymentList(boPaymentDTO);
    }
    public BoPaymentDTO selectPaymentDetail(BoPaymentDTO boPaymentDTO) {
        return boPaymentMapper.selectPaymentDetail(boPaymentDTO);
    }
    // 결제 리스트 count
    public int selectPaymentListCnt(BoPaymentDTO boPaymentDTO) {
        return boPaymentMapper.selectPaymentListCnt(boPaymentDTO);
    }
    @Transactional
    public void updatePayment(BoPaymentDTO boPaymentDTO) throws Exception {
        boPaymentMapper.updatePayment(boPaymentDTO);
    }
}
