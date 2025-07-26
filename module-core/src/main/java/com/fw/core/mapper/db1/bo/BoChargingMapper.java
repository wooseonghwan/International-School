package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoChargingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoChargingMapper {
    List<BoChargingDTO> selectGoodsUseHistoryList(BoChargingDTO boChargingDTO);
    int countGoodsUseHistoryList(BoChargingDTO boChargingDTO);
    List<BoChargingDTO> selectChargingHistoryList(BoChargingDTO boChargingDTO);
    int countChargingHistoryList(BoChargingDTO boChargingDTO);
    List<Map<String, Object>> selectTotalChargeAmountGroupedByCustNo(BoChargingDTO param);
}
