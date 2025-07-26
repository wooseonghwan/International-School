package com.fw.bo.system.menu.controller;

import com.fw.bo.system.menu.service.SettingMenuService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 메뉴 Controller
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class SettingMenuController {
    private final SettingMenuService menuService;

    /* 메뉴 관리 페이지 */
    @GetMapping("/bo/system/setting/menu")
    public String getMenu(ModelMap model){
        return "/bo/system/menu/menu";
    }

    /* 메뉴 리스트 취득 */
    @GetMapping("/bo/system/menu/menu-list")
    @ResponseBody
    public ResponseEntity<ResponseVO> selectMenuList(BoAdminMenuDTO boAdminMenuDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(menuService.selectMenuList()).build());
    }

    @PostMapping("/bo/system/menu/insert-menu")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertMenu(ModelMap model, @RequestBody BoAdminMenuDTO boAdminMenuDTO, HttpServletRequest request, HttpServletResponse response){
        ResponseCode code = ResponseCode.SUCCESS;
        try {
         menuService.insertMenu(boAdminMenuDTO);
        } catch (Exception e) {
            log.error("error",e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

}
