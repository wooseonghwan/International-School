package com.fw.bo.system.org.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.mapper.db1.bo.BoAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Admin Service
 */
@Service
@RequiredArgsConstructor
public class OrgAdminService {

	private final BoAdminMapper boAdminMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO) {
		return boAdminMapper.selectAdminList(boAdminDTO);
	}

	public int selectAdminListCnt(BoAdminDTO boAdminDTO) {
		return boAdminMapper.selectAdminListCnt(boAdminDTO);
	}

	/*public int selectIdCheck(String adminId) {
		return boAdminMapper.selectIdCheck(adminId);
	}*/

	public BoAdminDTO selectAdmin(BoAdminDTO boAdminDTO) {
		return boAdminMapper.selectAdmin(boAdminDTO);
	}

	public void insertAdmin(BoAdminDTO boAdminDTO) {
		//boAdminDTO.setAdminPassword(bCryptPasswordEncoder.encode(boAdminDTO.getAdminPassword()));
		boAdminMapper.insertAdmin(boAdminDTO);
	}

	public void updateAdmin(BoAdminDTO boAdminDTO) {
		boAdminMapper.updateAdmin(boAdminDTO);
	}

	public void deleteAdmin(BoAdminDTO boAdminDTO) {
		boAdminMapper.deleteAdmin(boAdminDTO);
	}


}
