package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoEstimateDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BoEstimateMapper {
    List<BoEstimateDTO> selectEstimateList(BoEstimateDTO boEstimateDTO);
    int selectEstimateListCnt(BoEstimateDTO boEstimateDTO);
    BoEstimateDTO selectEstimateInfo(String estimateId);
    void updateEstimate(BoEstimateDTO boEstimateDTO);
    void insertEstimateHistory(BoEstimateDTO boEstimateDTO);
    void insertPayment(BoEstimateDTO boEstimateDTO);
    List<String> selectEstimateHistoryList(String estimateId);
    BoEstimateDTO selectEstimateHistoryInfo(BoEstimateDTO boEstimateDTO);
}
