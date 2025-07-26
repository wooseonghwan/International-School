package com.fw.bo.system.menu.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuDTO;
import com.fw.core.mapper.db1.bo.BoAdminMenuMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingMenuService {

    private final BoAdminMenuMapper boAdminMenuMapper;

    public List<BoAdminMenuDTO> selectMenuList(){
        return boAdminMenuMapper.selectMenuList();
    }

    public void insertMenu(BoAdminMenuDTO boAdminMenuDTO){
        // 삭제할 메뉴 삭제
        for (BoAdminMenuDTO menuMasterModel : boAdminMenuDTO.getDeleteMenuMasterList()) {
            boAdminMenuMapper.deleteMenu(menuMasterModel);
        }

        // 등록, 수정 처리
        for (BoAdminMenuDTO menuMasterModel : boAdminMenuDTO.getMenuMasterList()) {
            boAdminMenuMapper.insertUpdateMenu(menuMasterModel);
        }
    }

    /**
     * 요청 URL로 상위 메뉴코드 조회
     */
    public String selectUpperMenuCdByUrl(String url) {
        return boAdminMenuMapper.selectUpperMenuCdByUrl(url);
    }

    /**
     * 메뉴 권한 리스트 조회
     */
    public List<BoAdminMenuDTO> selectMenuAuthList(BoAdminMenuDTO boAdminMenuDTO) {
        return boAdminMenuMapper.selectMenuAuthList(boAdminMenuDTO);
    }

    public Map<String, String> getMenuName(String uri){
        Map<String, String> map = new HashMap<>();
        map.put("current", boAdminMenuMapper.getMenuName(uri));
        map.put("upper", boAdminMenuMapper.getUpperMenuName(uri));
        map.put("detail", boAdminMenuMapper.getDetailMenuName(uri));
        return map;
    }

}
