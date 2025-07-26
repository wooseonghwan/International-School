package com.fw.bo.partner.controller;

import com.fw.bo.partner.service.BoPartnerService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoReportDTO;
import com.fw.core.vo.ResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BoPartnerController {
    private final BoPartnerService boPartnersService;
    @GetMapping("/bo/partner/partners-management")
    public String partnersManagement(ModelMap model, BoPartnerDTO boPartnerDTO) {
        if (boPartnerDTO.getSearchDate() == null || boPartnerDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boPartnerDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("partnerList", boPartnersService.selectPartnersAdList(boPartnerDTO));
        boPartnerDTO.setTotalCount(boPartnersService.selectPartnersAdListCnt(boPartnerDTO));
        model.addAttribute("searchInfo", boPartnerDTO);
        model.addAttribute("partnerAdInfo", boPartnersService.selectPartnersAdInfo(boPartnerDTO));
        model.addAttribute("partnerAdAmountInfo", boPartnersService.selectPartnersAdAmountInfo(boPartnerDTO));
        return "/bo/partner/partners-management";
    }

    @GetMapping("/bo/partner/status-change-history")
    @ResponseBody
    public List<BoPartnerDTO> getStatusChangeHistory(@RequestParam String partnersId, @RequestParam String adId) {
        BoPartnerDTO param = new BoPartnerDTO();
        param.setPartnersId(partnersId);
        param.setAdId(adId);
        return boPartnersService.selectPartnersStatusChangeList(param);
    }

    @GetMapping("/bo/partner/partners-management-detail")
    public String partnerDetail(@RequestParam("adId") String adId, ModelMap model) {
        BoPartnerDTO detail = boPartnersService.selectPartnersAdDetail(adId);
        model.addAttribute("detail", detail);
        return "bo/partner/partners-management-detail"; // Thymeleaf view 경로
    }

    @PostMapping("/bo/partner/update-partners-status")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePartnersStatus(@RequestBody BoPartnerDTO boPartnerDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boPartnersService.updatePartnersAdStatus(boPartnerDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boPartnerDTO.getErrorCode()).build());
    }

    @GetMapping("/bo/partner/partners-report")
    public String partnersReport(ModelMap model, BoReportDTO boReportDTO) {
        model.addAttribute("partnersReportList", boPartnersService.selectReportList(boReportDTO));
        boReportDTO.setTotalCount(boPartnersService.selectReportListCnt(boReportDTO));
        model.addAttribute("searchInfo", boReportDTO);
        return "/bo/partner/partners-report";
    }
}
