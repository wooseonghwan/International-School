package com.fw.bo.system.org.controller;

import com.fw.bo.system.org.service.OrgAdminService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 관리자 관리 Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrgAdminController {

    private final OrgAdminService orgAdminService;

    /**
     * 관리자 관리 페이지
     */
    @GetMapping("/bo/system/org/admin")
    public String org(ModelMap model, BoAdminDTO boAdminDTO) {
        model.addAttribute("list", orgAdminService.selectAdminList(boAdminDTO));
        boAdminDTO.setTotalCount(orgAdminService.selectAdminListCnt(boAdminDTO));
        model.addAttribute("searchInfo", boAdminDTO);
        return "bo/system/org/admin";
    }

    /**
     * 관리자 등록 페이지
     */
    @GetMapping("/bo/system/org/admin/form")
    public String adminForm(ModelMap model) {
        return "/bo/system/org/admin/form";
    }

    /**
     * 관리자 상세 페이지
     */
    @GetMapping("/bo/system/org/admin/detail")
    public String adminDetail(BoAdminDTO boadminDTO, ModelMap model) {
        model.addAttribute("detail", orgAdminService.selectAdmin(boadminDTO));
        return "/bo/system/org/admin/detail";
    }

    /**
     * 관리자 수정 페이지
     */
    @GetMapping("/bo/system/org/admin/modify")
    public String adminModify(BoAdminDTO boadminDTO, ModelMap model) {
        model.addAttribute("modify", orgAdminService.selectAdmin(boadminDTO));
        return "/bo/system/org/admin/modify";
    }

    /**
     * 관리자 관리 리스트 취득
     */
    @GetMapping("/bo/system/org/admin/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectAdminList(BoAdminDTO boadminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(orgAdminService.selectAdminList(boadminDTO)).build());
    }

    /**
     * 관리자 아이디 중복체크
     */
    /*@PostMapping("/bo/system/org/admin/idCheck")
    @ResponseBody
    public int idCheck(String adminId) {
        try {
            return orgAdminService.selectIdCheck(adminId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }*/

    /**
     * 관리자 등록
     */
    @PostMapping("/bo/system/org/admin/insert")
    @ResponseBody
    public ResponseEntity<ResponseVO> insert(@RequestBody BoAdminDTO boadminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            orgAdminService.insertAdmin(boadminDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    /**
     * 관리자 수정
     */
   /* @PostMapping("/bo/system/org/admin/update")
    @ResponseBody
    public ResponseEntity<ResponseVO> update(@RequestBody BoAdminDTO boadminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            orgAdminService.updateAdmin(boadminDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }*/


    /**
     * 관리자 삭제
     */
    @PostMapping("/bo/system/org/admin/delete")
    @ResponseBody
    public ResponseEntity<ResponseVO> delete(BoAdminDTO boadminDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            orgAdminService.deleteAdmin(boadminDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }
}
