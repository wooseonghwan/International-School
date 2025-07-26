package com.fw.bo.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 실패 Handler
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BoLoginAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessge = "로그인에 실패했습니다. 관리자에게 문의하세요.";

        if (exception != null) {
            errorMessge = exception.getMessage();
        }

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            /*
                Ajax의 .fail() / .error() 콜백은 HTTP 상태 코드가 4xx 또는 5xx 범위 사이에서 호출된다.
                로그인 실패 시 응답 상태코드를 401로 설정 -> .fail() / .error() 콜백으로 응답을 처리한다.
             */
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"errorMessage\":\"" + errorMessge + "\"}");
        } else {
            super.setDefaultFailureUrl("/bo/login");
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
