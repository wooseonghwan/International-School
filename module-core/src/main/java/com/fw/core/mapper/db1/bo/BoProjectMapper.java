package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoProjectDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoProjectMapper {
    List<BoProjectDTO> selectProjectList(BoProjectDTO boProjectDTO);
    int selectProjectListCnt(BoProjectDTO boProjectDTO);
    BoProjectDTO selectProjectDetail(String projectId);
    List<BoProjectDTO> selectApplyPartnerList(String projectId);
    void updateProject(BoProjectDTO boProjectDTO);
    List<BoProjectDTO> selectUserList(BoProjectDTO boProjectDTO);
    List<BoProjectDTO> selectEstimateList(String userId);
    void insertProject(BoProjectDTO boProjectDTO);
    void deleteProject(BoProjectDTO boProjectDTO);
}
