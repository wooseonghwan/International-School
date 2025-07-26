package com.fw.bo.dashboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.bo.dashboard.service.BoDashboardService;
import com.fw.bo.homepage.service.BoNoticeService;
import com.fw.core.dto.bo.BoMainDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class BoDashboardController {
    private final BoDashboardService boDashboardService;
    private final BoNoticeService boNoticeService;

    /*@GetMapping("/bo/dashboard")
    public String dashboard(ModelMap model, BoMainDTO boMainDTO, BoNoticeDTO boNoticeDTO) throws JsonProcessingException {
        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E)", Locale.KOREA);
        String formattedDate = today.format(formatter);
        // 오늘 날짜
        model.addAttribute("today", formattedDate);
        // 오늘의 할일 카운트
        model.addAttribute("todayInfo", boDashboardService.selectTodayTaskInfo(boMainDTO));
        // 공지사항
        model.addAttribute("noticeList", boNoticeService.selectNoticeList(boNoticeDTO));
        // 유입경로
        List<BoMainDTO> inflowList = boDashboardService.getInflowStats(); // INFLOW_PATH_NAME, COUNT 포함
        model.addAttribute("inflowStats", inflowList);
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("블로그", "#0076f3");
        colorMap.put("GFA", "#0076f3");
        colorMap.put("플레이스", "#cdd1d6");
        colorMap.put("홈페이지", "#00f382");
        colorMap.put("다음", "#55f300");
        colorMap.put("인스타", "#00f382");
        colorMap.put("유튜브", "blue");
        model.addAttribute("colorMap", colorMap);
        // 일별 매출
        List<BoMainDTO> dailyStats = boDashboardService.selectDailySales();
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        for (BoMainDTO dto : dailyStats) {
            labels.add(String.valueOf(dto.getDay())); // x축 레이블
            data.add(dto.getTotalPrice());            // y축 데이터
        }
        model.addAttribute("barLabels", labels);
        model.addAttribute("barData", data);
        // 방문자 수
        ObjectMapper mapper = new ObjectMapper();

        List<BoMainDTO> dailyVisitStats = boDashboardService.selectDailyVisitStats();   // 시간별 (0~23)
        List<BoMainDTO> weeklyVisitStats = boDashboardService.selectWeeklyVisitStats(); // 날짜별
        List<BoMainDTO> monthlyVisitStats = boDashboardService.selectMonthlyVisitStats(); // 날짜별

        // 일간
        model.addAttribute("dailyLabels", mapper.writeValueAsString(
                dailyVisitStats.stream().map(BoMainDTO::getLabel).collect(Collectors.toList())));
        model.addAttribute("dailyData", mapper.writeValueAsString(
                dailyVisitStats.stream().map(BoMainDTO::getCount).collect(Collectors.toList())));

        // 주간
        model.addAttribute("weeklyLabels", mapper.writeValueAsString(
                weeklyVisitStats.stream().map(BoMainDTO::getLabel).collect(Collectors.toList())));
        model.addAttribute("weeklyData", mapper.writeValueAsString(
                weeklyVisitStats.stream().map(BoMainDTO::getCount).collect(Collectors.toList())));

        // 월간
        model.addAttribute("monthlyLabels", mapper.writeValueAsString(
                monthlyVisitStats.stream().map(BoMainDTO::getLabel).collect(Collectors.toList())));
        model.addAttribute("monthlyData", mapper.writeValueAsString(
                monthlyVisitStats.stream().map(BoMainDTO::getCount).collect(Collectors.toList())));

        // 상품별 판매율
        List<BoMainDTO> productSalesList = boDashboardService.selectProductSales();
        List<String> productLabels = new ArrayList<>();
        List<Long> productCounts = new ArrayList<>();
        for (BoMainDTO dto : productSalesList) {
            productLabels.add(dto.getTitle());        // TITLE을 labels로
            productCounts.add(dto.getProductCnt());   // 판매 건수
        }
        model.addAttribute("productSalesLabels", new ObjectMapper().writeValueAsString(productLabels));
        model.addAttribute("productSalesCounts", new ObjectMapper().writeValueAsString(productCounts));
        return "/bo/dashboard";
    }*/

    @GetMapping("/bo/dashboard")
    public String dashboard(ModelMap model, BoMainDTO boMainDTO, BoNoticeDTO boNoticeDTO) throws JsonProcessingException {
        return "/bo/dashboard";
    }
}
