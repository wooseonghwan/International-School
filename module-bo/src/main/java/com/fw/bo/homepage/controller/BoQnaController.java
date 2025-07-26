package com.fw.bo.homepage.controller;

import com.fw.bo.homepage.service.BoQnaService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import com.fw.core.dto.bo.BoQnaDTO;
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
@RequestMapping("/bo/homepage/qna")
public class BoQnaController {

    private final BoQnaService boQnaService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @GetMapping({"", "/"})
    String qnaList(Model model, BoQnaDTO boQnaDTO){
        model.addAttribute("qnaList", boQnaService.selectQnaList(boQnaDTO));
        boQnaDTO.setTotalCount(boQnaService.selectQnaListCnt(boQnaDTO));
        model.addAttribute("searchInfo", boQnaDTO);
        return "/bo/homepage/qna";
    }

    @GetMapping("/detail")
    String qnaDetail(Model model, @RequestParam String qnaId){
        model.addAttribute("dto", boQnaService.selectQnaDetail(qnaId));
        return "/bo/homepage/qna-detail";
    }

    @GetMapping("/reply")
    String qnaReply(Model model,  @RequestParam String qnaId, HttpServletRequest request){
        BoAdminSessionDTO sessionDTO = (BoAdminSessionDTO) request.getSession().getAttribute(BO_SESSION_KEY);
        model.addAttribute("dto", boQnaService.selectQnaDetail(qnaId));
        model.addAttribute("adminDto", sessionDTO);
        return "/bo/homepage/qna-reply";
    }

    @PostMapping("/reply-update")
    @ResponseBody
    ResponseEntity<ResponseVO> qnaReply(Model model, @RequestBody BoQnaDTO boQnaDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        boQnaService.updateQnaReply(boQnaDTO);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}
