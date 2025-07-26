package com.fw.bo.partner.service;

import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoProjectDTO;
import com.fw.core.mapper.db1.bo.BoProjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BoProjectService {
    private final BoProjectMapper boProjectMapper;
    public List<BoProjectDTO> selectProjectList(BoProjectDTO boProjectDTO) {
        return boProjectMapper.selectProjectList(boProjectDTO);
    }
    public int selectProjectListCnt(BoProjectDTO boProjectDTO) {
        return boProjectMapper.selectProjectListCnt(boProjectDTO);
    }
    public BoProjectDTO selectProjectDetail(String projectId) {
        return boProjectMapper.selectProjectDetail(projectId);
    }
    public List<BoProjectDTO> selectApplyPartnerList(String projectId) {
        return boProjectMapper.selectApplyPartnerList(projectId);
    }

    public void updateProject(BoProjectDTO boProjectDTO) throws Exception {
        boProjectMapper.updateProject(boProjectDTO);
    }
    public List<BoProjectDTO> selectUserList(BoProjectDTO boProjectDTO) {
        return boProjectMapper.selectUserList(boProjectDTO);
    }
    public List<BoProjectDTO> selectEstimateList(String userId) {
        return boProjectMapper.selectEstimateList(userId);
    }
    public void insertProject(BoProjectDTO boProjectDTO) throws Exception {
        boProjectMapper.insertProject(boProjectDTO);
    }
    public void deleteProject(BoProjectDTO boProjectDTO) throws Exception {
        boProjectMapper.deleteProject(boProjectDTO);
    }
}
