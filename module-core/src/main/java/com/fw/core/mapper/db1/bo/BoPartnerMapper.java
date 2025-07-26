package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoPartnerMapper {
    List<BoPartnerDTO> selectPartnersAdList(BoPartnerDTO boPartnerDTO);
    int selectPartnersAdListCnt(BoPartnerDTO boPartnerDTO);
    BoPartnerDTO selectPartnersAdDetail(String adId);
    BoPartnerDTO selectPartnersAdInfo(BoPartnerDTO boPartnerDTO);
    BoPartnerDTO selectPartnersAdAmountInfo(BoPartnerDTO boPartnerDTO);
    List<BoPartnerDTO> selectPartnersStatusChangeList(BoPartnerDTO boPartnerDTO);
    void updatePartnersAdStatus(BoPartnerDTO boPartnerDTO);
    String selectCurrentStatus(String adId);
    void insertPartnersStatusHistory(BoPartnerDTO boPartnerDTO);
    List<BoReportDTO> selectReportList(BoReportDTO boReportDTO);
    int selectReportListCnt(BoReportDTO boReportDTO);
}
