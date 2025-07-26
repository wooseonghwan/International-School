package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoFaqDTO;

import java.util.List;

public interface BoFaqMapper {
    List<BoFaqDTO> selectFaqList(BoFaqDTO dto);
    int selectFaqListCnt(BoFaqDTO dto);
    void insertFaq(BoFaqDTO dto);
    BoFaqDTO selectFaqDetail(String faqId);
    void updateFaq(BoFaqDTO dto);
}
