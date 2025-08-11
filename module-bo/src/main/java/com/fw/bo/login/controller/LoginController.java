package com.fw.bo.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.bo.config.security.BoLoginService;
import com.fw.bo.dashboard.service.BoDashboardService;
import com.fw.bo.homepage.service.BoNoticeService;
import com.fw.bo.system.org.service.OrgAdminService;
import com.fw.bo.user.service.BoUserService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoMainDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 로그인 Controller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final BoLoginService boLoginService;
    private final BoDashboardService boDashboardService;
    private final BoNoticeService boNoticeService;
    private final OrgAdminService boAdminService;
    /**
     * BO Dashboard 페이지
     */
    @GetMapping({"/", "/bo"})
    public String bo(ModelMap model, BoMainDTO boMainDTO, BoAdminDTO boAdminDTO) throws JsonProcessingException {
        model.addAttribute("adminList", boAdminService.selectAdminList(boAdminDTO));
        return "/bo/user/reg-admin";
    }
    /**
     * BO 로그인 페이지
     */
    @GetMapping("/bo/login")
    public String login(
            @ModelAttribute("errorMessage") String errorMessage
            , ModelMap modelMap) throws Exception {
        log.info("errorMessage :: {}", errorMessage);
        return "/bo/login";
    }

    @PostMapping("/email/check")
    @ResponseBody
    public int emailCheck(String email) {
        try {
            return boLoginService.selectEmailCheck(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    @PostMapping("/change/password")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePassword(@RequestBody BoAdminDTO boadminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boLoginService.updatePassword(boadminDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


}