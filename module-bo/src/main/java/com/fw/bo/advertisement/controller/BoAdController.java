package com.fw.bo.advertisement.controller;

import com.fw.bo.advertisement.service.BoAdService;
import com.fw.bo.payment.service.BoPaymentService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdDTO;
import com.fw.core.dto.bo.BoEstimateDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.vo.ResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
@Slf4j
public class BoAdController {
    private final BoAdService boAdService;

    @GetMapping("/bo/advertisement/ad-history")
    public String adHistory(ModelMap model, BoAdDTO boAdDTO) {
        if (boAdDTO.getSearchDate() == null || boAdDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boAdDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("adHistoryList", boAdService.selectAdHistoryList(boAdDTO));
        boAdDTO.setTotalCount(boAdService.selectAdHistoryListCnt(boAdDTO));
        model.addAttribute("searchInfo", boAdDTO);
        return "/bo/advertisement/ad-history";
    }

    @GetMapping("/bo/advertisement/ad-history-detail")
    public String adHistoryDetail(BoAdDTO boAdDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        BoAdDTO detail = boAdService.selectAdHistoryDetail(boAdDTO);
        model.addAttribute("detail", detail);
        return "/bo/advertisement/ad-history-detail";
    }

    @PostMapping("/bo/advertisement/update-ad-history")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateAdHistory(@RequestBody BoAdDTO boAdDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boAdService.updateAdHistory(boAdDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boAdDTO.getErrorCode()).build());
    }
}
