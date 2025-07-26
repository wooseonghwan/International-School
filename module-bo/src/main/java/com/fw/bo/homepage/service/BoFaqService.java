package com.fw.bo.homepage.service;

import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.mapper.db1.bo.BoFaqMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoFaqService {

    private final BoFaqMapper boFaqMapper;

    public List<BoFaqDTO> selectFaqList(BoFaqDTO boFaqDTO) {
        return boFaqMapper.selectFaqList(boFaqDTO);
    }

    public int selectFaqListCnt(BoFaqDTO boFaqDTO) {
        return boFaqMapper.selectFaqListCnt(boFaqDTO);
    }

    public void insertFaq(BoFaqDTO boFaqDTO) {
        boFaqMapper.insertFaq(boFaqDTO);
    }

    public BoFaqDTO selectFaqDetail(String faqId) {
        return boFaqMapper.selectFaqDetail(faqId);
    }

    public void updateFaq(BoFaqDTO boFaqDTO) {
        boFaqMapper.updateFaq(boFaqDTO);
    }

}
