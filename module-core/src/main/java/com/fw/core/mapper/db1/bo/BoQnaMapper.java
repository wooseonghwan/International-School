package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoQnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoQnaMapper {
   List<BoQnaDTO> selectQnaList(BoQnaDTO dto);
   int selectQnaListCnt(BoQnaDTO dto);
   BoQnaDTO selectQnaDetail(String qnaId);
   void updateQnaReply(BoQnaDTO dto);
}
