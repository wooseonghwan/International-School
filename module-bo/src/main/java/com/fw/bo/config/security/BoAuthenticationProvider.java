package com.fw.bo.config.security;

import com.fw.core.dto.bo.BoAdminDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Bo Authentication Provider
 */
@Slf4j
public class BoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BoLoginService boLoginService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("Authentication Provider >> ");

        String adminId = (String) authentication.getPrincipal();
        String adminPassword = (String) authentication.getCredentials();

        BoAdminDTO boAdminDTO = BoAdminDTO.builder()
                                          .adminId(adminId)
                                          .adminPassword(adminPassword)
                                          .build();

        // TODO :: 계정에 대한 조건 점검

        BoAdminDTO adminInfo = boLoginService.selectAdminForAdminId(boAdminDTO);
        if (adminInfo == null) {
            throw new UsernameNotFoundException("존재 하지 않습니다.");     // TODO :: MESSAGE 처리
        }

       //if (adminInfo.getPasswordFailCount() > 4) {
       //    throw new LockedException("비밀번호를 틀린 횟수가 5회로 계정이 잠겼습니다.");     // TODO :: MESSAGE 처리
       //}

//       if (!bCryptPasswordEncoder.matches(boAdminDTO.getAdminPassword(), adminInfo.getAdminPassword())) {
//           throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//       }
        if(!adminInfo.getAdminPassword().equals(adminPassword)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

       //if (adminInfo.isAccountNonLocked()) {
       //    throw new LockedException("정지된 계정입니다.");
       //}

       //if (adminInfo.isAccountNonExpired()) {
       //    throw new AccountExpiredException("유효기간이 만료된 계정입니다.");
       //}

       //if (adminInfo.isEnabled()) {
       //    throw new DisabledException("비활성화된 계정입니다.");
       //}

        return new UsernamePasswordAuthenticationToken(adminInfo, authentication.getCredentials(), adminInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
