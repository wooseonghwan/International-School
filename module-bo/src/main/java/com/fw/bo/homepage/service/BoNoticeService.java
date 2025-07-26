package com.fw.bo.homepage.service;

import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.mapper.db1.bo.BoNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoNoticeService {

    private final BoNoticeMapper boNoticeMapper;

    public List<BoNoticeDTO> selectNoticeList(BoNoticeDTO boNoticeDTO){
        return boNoticeMapper.selectNoticeList(boNoticeDTO);
    }

    public int selectNoticeListCnt(BoNoticeDTO boNoticeDTO){
        return boNoticeMapper.selectNoticeListCnt(boNoticeDTO);
    }

    public void insertNotice(BoNoticeDTO boNoticeDTO){
        boNoticeMapper.insertNotice(boNoticeDTO);
    }

    public BoNoticeDTO selectNoticeDetail(String noticeId){
        return boNoticeMapper.selectNoticeDetail(noticeId);
    }

    public void updateNotice(BoNoticeDTO boNoticeDTO){
        boNoticeMapper.updateNotice(boNoticeDTO);
    }
    public void deleteNotices(List<Long> noticeIds) {
        boNoticeMapper.deleteNotices(noticeIds);
    }

}
