package com.fw.bo.estimate.controller;

import com.fw.bo.estimate.service.BoEstimateService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoEstimateDTO;
import com.fw.core.vo.ResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@AllArgsConstructor
public class BoEstimateController {
    private final BoEstimateService boEstimateService;
    @GetMapping("/bo/estimate/estimate-management")
    public String estimate(ModelMap model, BoEstimateDTO boEstimateDTO) {
        if (boEstimateDTO.getSearchDate() == null || boEstimateDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boEstimateDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("estimateList", boEstimateService.selectEstimateList(boEstimateDTO));
        boEstimateDTO.setTotalCount(boEstimateService.selectEstimateListCnt(boEstimateDTO));
        model.addAttribute("searchInfo", boEstimateDTO);

        return "/bo/estimate/estimate-management";
    }
    @GetMapping("/bo/estimate/estimate-management-detail")
    public String projectDetail(@RequestParam("estimateId") String estimateId, ModelMap model) {
        BoEstimateDTO detail = boEstimateService.selectEstimateInfo(estimateId);
        model.addAttribute("detail", detail);
        model.addAttribute("estimateHistoryList", boEstimateService.selectEstimateHistoryList(estimateId));
        return "/bo/estimate/estimate-management-detail";
    }
    @PostMapping("/bo/estimate/update-estimate")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateEstimate(@RequestBody BoEstimateDTO boEstimateDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boEstimateService.updateEstimate(boEstimateDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boEstimateDTO.getErrorCode()).build());
    }
    @GetMapping("/bo/estimate/estimate-history-detail")
    @ResponseBody
    public ResponseEntity<BoEstimateDTO> getEstimateHistoryDetail(
            @RequestParam("estimateId") String estimateId,
            @RequestParam("createDt") String createDt) {
        BoEstimateDTO detail = boEstimateService.selectEstimateHistoryInfo(estimateId, createDt);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(detail);
    }
    @GetMapping("/bo/estimate/content-mix")
    public String contentMix(ModelMap model, BoEstimateDTO boEstimateDTO) {
        if (boEstimateDTO.getSearchDate() == null || boEstimateDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boEstimateDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("estimateList", boEstimateService.selectEstimateList(boEstimateDTO));
        boEstimateDTO.setTotalCount(boEstimateService.selectEstimateListCnt(boEstimateDTO));
        model.addAttribute("searchInfo", boEstimateDTO);

        return "/bo/estimate/content-mix";
    }
    @GetMapping("/bo/estimate/estimate-db")
    public String estimateDb(ModelMap model, BoEstimateDTO boEstimateDTO) {
        if (boEstimateDTO.getSearchDate() == null || boEstimateDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boEstimateDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("estimateList", boEstimateService.selectEstimateList(boEstimateDTO));
        boEstimateDTO.setTotalCount(boEstimateService.selectEstimateListCnt(boEstimateDTO));
        model.addAttribute("searchInfo", boEstimateDTO);

        return "/bo/estimate/estimate-db";
    }
}
