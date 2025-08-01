package com.fw.bo.user.controller;

import com.fw.bo.MailService;
import com.fw.bo.system.org.service.OrgAdminService;
import com.fw.bo.user.service.BoUserService;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoUserDTO;
import com.fw.core.util.AesUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BoUserController {
    private final BoUserService boUserService;
    private final OrgAdminService boAdminService;
    private final MailService mailService;

    @GetMapping("/bo/user")
    public String user(ModelMap model, BoUserDTO boUserDTO) {
        List<BoUserDTO> userList = boUserService.selectUserList(boUserDTO);

        for (BoUserDTO user : userList) {
            String originalPw = user.getWebPw();
            if (originalPw != null && !originalPw.isEmpty()) {
                try {
                    // 복호화 시도
                    String decryptedPw = AesUtil.decrypt(originalPw);
                    user.setWebPw(decryptedPw); // 복호화 성공 시 교체
                } catch (Exception e) {
                    // 복호화 실패 시 원래 암호화된 값을 그대로 유지
                    user.setWebPw(originalPw);
                }
            }
        }
        model.addAttribute("userList", userList);
        boUserDTO.setTotalCount(boUserService.countUserList(boUserDTO));
        model.addAttribute("searchInfo", boUserDTO);
        return "/bo/user/user";
    }


    @GetMapping("/bo/reg-admin")
    public String regAdmin(ModelMap model, BoAdminDTO boAdminDTO){
        model.addAttribute("adminList", boAdminService.selectAdminList(boAdminDTO));
        model.addAttribute("searchInfo", boAdminDTO);
        return "/bo/user/reg-admin";
    }

    @PostMapping("/bo/admin-insert")
    @ResponseBody
    public ResponseEntity<?> insertAdmin(@RequestBody BoAdminDTO boAdminDTO) {
        boAdminService.insertAdmin(boAdminDTO);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/bo/admin-update")
    @ResponseBody
    public ResponseEntity<?> updateAdmin(@RequestBody BoAdminDTO boAdminDTO) {
        boAdminService.updateAdmin(boAdminDTO);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/bo/admin-delete")
    @ResponseBody
    public ResponseEntity<?> deleteAdmin(@RequestBody BoAdminDTO boAdminDTO) {
        boAdminService.deleteAdmin(boAdminDTO);
        return ResponseEntity.ok().build();
    }
}
