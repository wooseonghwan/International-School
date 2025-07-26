package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.CommonDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoCommonMapper {

	/**
	 * 공통코드 리스트 조회
	 */
	List<CommonDTO> selectCommonCd(CommonDTO commonDTO);

}
