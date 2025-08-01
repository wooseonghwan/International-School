package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoMenuMapper {
    void insertMenu(BoMenuDTO boMenuDTO);
    void updateMenu(BoMenuDTO boMenuDTO);
    List<BoMenuDTO> selectMenuFileName();
}
