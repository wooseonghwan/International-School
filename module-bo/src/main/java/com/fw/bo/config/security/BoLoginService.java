package com.fw.bo.config.security;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.mapper.db1.bo.BoAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 로그인 서비스
 * @author dongbeom
 */
@Service
@RequiredArgsConstructor
public class BoLoginService {

    private final BoAdminMapper boAdminMapper;

    public BoAdminDTO selectAdminForAdminId(BoAdminDTO boAdminDTO) {
        return boAdminMapper.selectAdminForAdminId(boAdminDTO);
    }
    public int selectEmailCheck(String email) {
        return boAdminMapper.selectEmailCheck(email);
    }
    public void updatePassword(BoAdminDTO boAdminDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boAdminDTO.setAdminPassword(bCryptPasswordEncoder.encode(boAdminDTO.getAdminPassword()));
        boAdminMapper.updatePassword(boAdminDTO);
        boAdminMapper.updatePasswordChangeDt(boAdminDTO);
    }

}