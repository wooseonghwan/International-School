package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdDTO;
import com.fw.core.dto.bo.BoEstimateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAdMapper {
    List<BoAdDTO> selectAdHistoryList(BoAdDTO boAdDTO);
    int selectAdHistoryListCnt(BoAdDTO boAdDTO);
    BoAdDTO selectAdHistoryDetail(BoAdDTO boAdDTO);
    void updateAdHistory(BoAdDTO boAdDTO);
    void insertAdPayment(BoAdDTO boAdDTO);
}
