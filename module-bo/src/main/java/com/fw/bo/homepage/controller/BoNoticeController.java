package com.fw.bo.homepage.controller;

import com.fw.bo.homepage.service.BoNoticeService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/bo/homepage/notice")
public class BoNoticeController {

    private final BoNoticeService boNoticeService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @GetMapping({"", "/"})
    String notice(Model model, BoNoticeDTO boNoticeDTO){

        model.addAttribute("noticeList", boNoticeService.selectNoticeList(boNoticeDTO));
        boNoticeDTO.setTotalCount(boNoticeService.selectNoticeListCnt(boNoticeDTO));
        model.addAttribute("searchInfo", boNoticeDTO);
        return "/bo/homepage/notice";
    }

    @GetMapping("/reg")
    String noticeRegister(Model model){

        return "/bo/homepage/notice-form";
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseVO> insertNotice(HttpServletRequest request, @RequestBody BoNoticeDTO boNoticeDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        BoAdminSessionDTO sessionDTO = (BoAdminSessionDTO) request.getSession().getAttribute(BO_SESSION_KEY);
        boNoticeDTO.setCreateId(sessionDTO.getAdminNm());
        boNoticeService.insertNotice(boNoticeDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    @GetMapping("/detail")
    String noticeDetail(Model model, @RequestParam String noticeId){
        model.addAttribute("dto", boNoticeService.selectNoticeDetail(noticeId));
        return "/bo/homepage/notice-detail";
    }

    @PostMapping("/update")
    ResponseEntity<ResponseVO> updateNotice(HttpServletRequest request, @RequestBody BoNoticeDTO boNoticeDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        BoAdminSessionDTO sessionDTO = (BoAdminSessionDTO) request.getSession().getAttribute(BO_SESSION_KEY);
        boNoticeDTO.setCreateId(sessionDTO.getAdminNm());
        boNoticeService.updateNotice(boNoticeDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteNotices(@RequestBody Map<String, List<Long>> param) {
        List<Long> noticeIds = param.get("noticeIds");
        boNoticeService.deleteNotices(noticeIds);
        return ResponseEntity.ok().build();
    }

}
