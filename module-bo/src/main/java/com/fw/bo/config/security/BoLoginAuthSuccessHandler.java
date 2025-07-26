package com.fw.bo.config.security;

import com.fw.bo.system.menu.service.SettingMenuService;
import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 성공 Handler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final BoLoginService boLoginService;
    private final SettingMenuService settingMenuService;
    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        BoAdminDTO boAdminDTO = (BoAdminDTO) authentication.getPrincipal();
        BoAdminSessionDTO boAdminSessionDTO = BoAdminSessionDTO.builder().adminSeq(boAdminDTO.getAdminSeq())
                                                                         .orgId(boAdminDTO.getOrgId())
                                                                         .adminId(boAdminDTO.getAdminId())
                                                                         .adminNm(boAdminDTO.getAdminNm())
                                                                         .adminPhoneNm(boAdminDTO.getAdminPhoneNm())
                                                                         .lastPasswordChangeDt(boAdminDTO.getLastPasswordChangeDt())
                                                                         .email(boAdminDTO.getEmail())
                                                                         .groupCd(boAdminDTO.getGroupCd())
                                                                         .groupNm(boAdminDTO.getGroupNm())
                                                                         .build();

        request.getSession().setAttribute(BO_SESSION_KEY, boAdminSessionDTO);
        request.getSession().setAttribute("adminId", boAdminDTO.getAdminId());
        request.getSession().setAttribute("adminNm", boAdminDTO.getAdminNm());
        // session 사용자 메뉴권한 리스트
        request.getSession().setAttribute("MENU_AUTH", settingMenuService.selectMenuAuthList(BoAdminMenuDTO.builder().groupCd(boAdminDTO.getGroupCd()).build()));

        // 비밀번호 실패 횟수 초기화
        //boLoginService.updateResetPasswordFailCount(boAdminDTO);
        response.sendRedirect("/");
    }

}
