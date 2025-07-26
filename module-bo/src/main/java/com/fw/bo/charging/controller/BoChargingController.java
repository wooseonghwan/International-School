package com.fw.bo.charging.controller;

import com.fw.bo.charging.service.BoChargingService;
import com.fw.core.dto.bo.BoChargingDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class BoChargingController {
    private final BoChargingService boChargingService;

    @GetMapping("/bo/goods-use-history")
    public String goodsUseHistoryList(ModelMap model, BoChargingDTO boChargingDTO){
        Map<String, Integer> custTotalMap = boChargingService.getTotalChargeAmountGroupedByCustNo(boChargingDTO);
        if (custTotalMap == null) {
            custTotalMap = new HashMap<>();
        }
        model.addAttribute("goodsUseHistoryList", boChargingService.selectGoodsUseHistoryList(boChargingDTO));
        boChargingDTO.setTotalCount(boChargingService.countGoodsUseHistoryList(boChargingDTO));
        model.addAttribute("custTotalMap", custTotalMap);
        model.addAttribute("searchInfo", boChargingDTO);
        return "/bo/charging/goods-use-history";
    }
    @GetMapping("/bo/charging-history")
    public String chargingHistoryList(ModelMap model, BoChargingDTO boChargingDTO){
        model.addAttribute("chargingList", boChargingService.selectChargingHistoryList(boChargingDTO));
        boChargingDTO.setTotalCount(boChargingService.countChargingHistoryList(boChargingDTO));
        model.addAttribute("searchInfo", boChargingDTO);
        return "/bo/charging/charging-history";
    }
}
