package com.fw.bo.homepage.service;

import com.fw.core.dto.bo.BoMenuDTO;
import com.fw.core.mapper.db1.bo.BoMenuMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BoMenuService {
    private final BoMenuMapper boMenuMapper;
    @Transactional
    public void insertMenu(BoMenuDTO boMenuDTO) throws Exception {
        boMenuMapper.insertMenu(boMenuDTO);
    }

    @Transactional
    public void updateMenu(BoMenuDTO boMenuDTO) throws Exception {
        boMenuMapper.updateMenu(boMenuDTO);
    }

    public List<BoMenuDTO> selectMenuFileName() {
        return boMenuMapper.selectMenuFileName();
    }
}
