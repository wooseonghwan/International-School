package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAdminMenuMapper {

    /**
     * 트리리스트 취득
     */
    List<BoAdminMenuDTO> selectMenuList();

    /**
     * 메뉴 삭제
     */
    void deleteMenu(BoAdminMenuDTO boAdminMenuDTO);

    /**
     * 메뉴 추가,업데이트
     */
    void insertUpdateMenu(BoAdminMenuDTO boAdminMenuDTO);

    /**
     * 요청 URL로 상위 메뉴코드 조회
     */
    String selectUpperMenuCdByUrl(String url);

    /**
     * 메뉴 권한 리스트 조회
     */
    List<BoAdminMenuDTO> selectMenuAuthList(BoAdminMenuDTO boAdminMenuDTO);

    String getUpperMenuName(String menuUrl);
    String getMenuName(String menuUrl);
    String getDetailMenuName(String menuUrl);
}