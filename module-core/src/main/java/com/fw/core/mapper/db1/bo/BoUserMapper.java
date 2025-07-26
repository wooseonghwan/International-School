package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.dto.bo.BoUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoUserMapper {
    List<BoUserDTO> selectUserList(BoUserDTO boUserDTO);
    List<BoUserDTO> selectBlacklist(BoUserDTO boUserDTO);
    int countUserList(BoUserDTO boUserDTO);
    int countBlacklist(BoUserDTO boUserDTO);
    int setUserToBlacklist(BoUserDTO boUserDTO);
    BoUserDTO selectUserDetail(BoUserDTO boUserDTO);
    void updateUser(BoUserDTO boUserDTO);
    List<BoUserDTO> selectUserHistory(BoUserDTO boUserDTO);
    List<BoUserDTO> selectPartnersList(BoUserDTO boUserDTO);
    int selectPartnersListCnt(BoUserDTO boUserDTO);
    void insertBlacklist(BoUserDTO boUserDTO);
    void deleteBlacklist(BoUserDTO boUserDTO);
    BoUserDTO selectPartnersDetail(BoUserDTO boUserDto);
    void updatePartners(BoUserDTO boUserDTO);
    void updatePassword(String userId, String encodedPassword);
}
