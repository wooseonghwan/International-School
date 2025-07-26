package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAdminMapper {

    List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO);
    int selectAdminListCnt(BoAdminDTO boAdminDTO);
    int selectEmailCheck(String email);
    BoAdminDTO selectAdmin(BoAdminDTO boAdminDTO);
    BoAdminDTO selectAdminForAdminId(BoAdminDTO boAdminDTO);
    void insertAdmin(BoAdminDTO boAdminDTO);
    void updateAdmin(BoAdminDTO boAdminDTO);
    void updatePassword(BoAdminDTO boAdminDTO);
    void updatePasswordChangeDt(BoAdminDTO boAdminDTO);
    void deleteAdmin(BoAdminDTO boAdminDTO);
}
