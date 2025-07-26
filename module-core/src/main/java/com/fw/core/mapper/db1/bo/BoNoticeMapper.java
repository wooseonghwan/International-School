package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoNoticeMapper {
    List<BoNoticeDTO> selectNoticeList(BoNoticeDTO dto);
    int selectNoticeListCnt(BoNoticeDTO dto);
    void insertNotice(BoNoticeDTO dto);
    BoNoticeDTO selectNoticeDetail(String noticeId);
    void updateNotice(BoNoticeDTO dto);
    void deleteNotices(List<Long> noticeIds);
}
