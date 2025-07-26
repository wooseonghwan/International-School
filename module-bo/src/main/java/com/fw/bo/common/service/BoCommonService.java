package com.fw.bo.common.service;

import com.fw.core.dto.CommonDTO;
import com.fw.core.mapper.db1.bo.BoCommonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoCommonService {

	private final BoCommonMapper boCommonMapper;

	public List<CommonDTO> selectCommonCd(CommonDTO commonDTO) {
		return boCommonMapper.selectCommonCd(commonDTO);
	}

}
