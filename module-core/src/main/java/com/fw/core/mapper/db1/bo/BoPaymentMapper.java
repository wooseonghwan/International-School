package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoPaymentMapper {
    List<BoPaymentDTO> selectPaymentList(BoPaymentDTO boPaymentDTO);
    int selectPaymentListCnt(BoPaymentDTO boPaymentDTO);
    BoPaymentDTO selectPaymentDetail(BoPaymentDTO boPaymentDTO);
    void updatePayment(BoPaymentDTO boPaymentDTO);
}
