package com.fw.bo.mail.controller;

//import com.fw.bo.confirmation.service.BoConfirmationService;
import com.fw.bo.mail.service.BoMailService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoMailDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoMailController {

    private final BoMailService boMailService;
    @PostMapping("/contact/send")
    @ResponseBody
    public ResponseEntity<ResponseVO> simulationSubmit(@RequestBody BoMailDTO boMailDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;
        boolean result = false;
        try {
            String authCode = boMailService.sendSimpleMessage(boMailDTO);
            request.getSession().setAttribute("AUTH_CODE", authCode);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data("").build());


    }

    @PostMapping("/contact/send/check")
    @ResponseBody
    public ResponseEntity<ResponseVO> check(@RequestBody BoMailDTO boMailDTO, HttpServletRequest request) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {

            String authCode = (String) request.getSession().getAttribute("AUTH_CODE");
            if (StringUtils.isBlank(authCode)) {
                code = ResponseCode.BAD_REQUEST;
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data("").build());
            }

            if (!boMailDTO.getAuthCode().equals(authCode)) {
                code = ResponseCode.BAD_REQUEST;
                return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data("").build());
            }

        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data("").build());


    }

}
