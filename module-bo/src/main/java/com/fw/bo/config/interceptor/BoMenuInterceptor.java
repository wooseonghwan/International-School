package com.fw.bo.config.interceptor;

import com.fw.bo.system.menu.service.SettingMenuService;
import com.fw.core.dto.bo.BoAdminSessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 메뉴 획득 인터셉터
 */
@Slf4j
public class BoMenuInterceptor implements HandlerInterceptor {

    @Autowired
    private SettingMenuService menuService;

    @Value("${bo.session.key-name}")
    private String BO_SESSION_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // AJAX 요청시 제외
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        HttpSession session = request.getSession();
        BoAdminSessionDTO boAdminSessionDTO = (BoAdminSessionDTO)session.getAttribute(BO_SESSION_KEY);

        if(boAdminSessionDTO == null){
            request.getSession().invalidate();
            response.sendRedirect("/bo/login");
        }

        request.setAttribute("sessionInfo", boAdminSessionDTO);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        /*
          활성화된 메뉴 active/show 처리
         */

        // 요청 URL 'activeMenuURI' -> _sidebar.html 에서 현재 페이지에 해당되는 메뉴 active/show 처리
        String requestURI = request.getRequestURI();
        request.setAttribute("activeMenuURI", requestURI);

        // 요청 URL로 해당 메뉴의 상위메뉴 코드를 가져옴 -> _sidebar.html 에서 현재 페이지에 해당되는 상위메뉴 active/show 처리
        String activeUpperMenuCd = menuService.selectUpperMenuCdByUrl(requestURI);
        request.setAttribute("activeUpperMenuCd", activeUpperMenuCd);

        //상위 메뉴, 현재 메뉴 이름
        request.setAttribute("menuNm", menuService.getMenuName(requestURI));

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}