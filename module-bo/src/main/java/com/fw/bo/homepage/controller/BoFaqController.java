package com.fw.bo.homepage.controller;

import com.fw.bo.homepage.service.BoFaqService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bo/homepage/faq")
public class BoFaqController {
    private final BoFaqService boFaqService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @GetMapping({"", "/"})
    String faqList(Model model, BoFaqDTO boFaqDTO){

        model.addAttribute("faqList", boFaqService.selectFaqList(boFaqDTO));
        boFaqDTO.setTotalCount(boFaqService.selectFaqListCnt(boFaqDTO));
        model.addAttribute("searchInfo", boFaqDTO);
        return "/bo/homepage/faq";
    }

    @GetMapping("/reg")
    String fagRegister(Model model){
        return "/bo/homepage/faq-form";
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseVO> insertFaq(HttpServletRequest request, @RequestBody BoFaqDTO boFaqDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        boFaqService.insertFaq(boFaqDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    @GetMapping("/detail")
    String updateFaq(Model model, @RequestParam String faqId){

        BoFaqDTO dto = boFaqService.selectFaqDetail(faqId);
        model.addAttribute("dto", dto);
        System.out.println(dto);
        return "/bo/homepage/faq-detail";
    }

    @PostMapping("/update")
    ResponseEntity<ResponseVO> updateFaq(HttpServletRequest request, @RequestBody BoFaqDTO boFaqDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        boFaqService.updateFaq(boFaqDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}
