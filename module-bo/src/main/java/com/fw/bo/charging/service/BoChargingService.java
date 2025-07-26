package com.fw.bo.charging.service;

import com.fw.core.dto.bo.BoChargingDTO;
import com.fw.core.mapper.db1.bo.BoChargingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BoChargingService {
    private final BoChargingMapper boChargingMapper;

    public List<BoChargingDTO> selectGoodsUseHistoryList(BoChargingDTO boChargingDTO) {
        return boChargingMapper.selectGoodsUseHistoryList(boChargingDTO);
    }
    public List<BoChargingDTO> selectChargingHistoryList(BoChargingDTO boChargingDTO) {
        return boChargingMapper.selectChargingHistoryList(boChargingDTO);
    }
    public int countGoodsUseHistoryList(BoChargingDTO boChargingDTO) {
        return boChargingMapper.countGoodsUseHistoryList(boChargingDTO);
    }
    public int countChargingHistoryList(BoChargingDTO boChargingDTO) {
        return boChargingMapper.countChargingHistoryList(boChargingDTO);
    }
    public Map<String, Integer> getTotalChargeAmountGroupedByCustNo(BoChargingDTO param) {
        List<Map<String, Object>> rawList = boChargingMapper.selectTotalChargeAmountGroupedByCustNo(param);

        return rawList.stream()
                .filter(m -> m.get("custNo") != null)
                .collect(Collectors.toMap(
                        m -> String.valueOf(m.get("custNo")),
                        m -> {
                            Object value = m.get("totalAmount");
                            if (value instanceof Number) {
                                return ((Number) value).intValue();
                            } else if (value instanceof String) {
                                try {
                                    return Integer.parseInt((String) value);
                                } catch (NumberFormatException e) {
                                    return 0; // 안전하게 처리
                                }
                            } else {
                                return 0;
                            }
                        }
                ));
    }
}
