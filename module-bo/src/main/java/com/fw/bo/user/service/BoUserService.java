package com.fw.bo.user.service;

import com.fw.core.dto.bo.BoUserDTO;
import com.fw.core.mapper.db1.bo.BoUserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoUserService {
    private final BoUserMapper boUserMapper;

    public List<BoUserDTO> selectUserList(BoUserDTO boUserDTO) {
        return boUserMapper.selectUserList(boUserDTO);
    }
    public List<BoUserDTO> selectBlacklist(BoUserDTO boUserDTO) {
        return boUserMapper.selectBlacklist(boUserDTO);
    }
    public int countUserList(BoUserDTO boUserDTO) {
        return boUserMapper.countUserList(boUserDTO);
    }
    public int countBlacklist(BoUserDTO boUserDTO) {
        return boUserMapper.countBlacklist(boUserDTO);
    }
    public int setUserToBlacklist(BoUserDTO boUserDTO) {
        return boUserMapper.setUserToBlacklist(boUserDTO);
    }
    public BoUserDTO selectUserDetail(BoUserDTO boUserDTO) {
        return boUserMapper.selectUserDetail(boUserDTO);
    }
    public void updateUser(BoUserDTO boUserDTO){
        boUserMapper.updateUser(boUserDTO);
    }
    public List<BoUserDTO> selectUserHistory(BoUserDTO boUserDTO) {
        return boUserMapper.selectUserHistory(boUserDTO);
    }

    public List<BoUserDTO> selectPartnersList(BoUserDTO boUserDTO) {
        return boUserMapper.selectPartnersList(boUserDTO);
    }
    public int selectPartnersListCnt(BoUserDTO boUserDTO){
        return boUserMapper.selectPartnersListCnt(boUserDTO);
    }

    public void insertBlacklist(BoUserDTO boUserDTO) throws Exception {
        boUserMapper.insertBlacklist(boUserDTO);
    }
    public void deleteBlacklist(BoUserDTO boUserDTO) throws Exception {
        boUserMapper.deleteBlacklist(boUserDTO);
    }
    public BoUserDTO selectPartnersDetail(BoUserDTO boUserDTO) {
        return boUserMapper.selectPartnersDetail(boUserDTO);
    }
    public void updatePartners(BoUserDTO boUserDTO){
        boUserMapper.updatePartners(boUserDTO);
    }
    public void updatePassword(String userId, String encodedPassword) {
        boUserMapper.updatePassword(userId, encodedPassword);
    }
}
