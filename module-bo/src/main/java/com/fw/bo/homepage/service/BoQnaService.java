package com.fw.bo.homepage.service;

import com.fw.core.dto.bo.BoQnaDTO;
import com.fw.core.mapper.db1.bo.BoQnaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoQnaService {

    private final BoQnaMapper boQnaMapper;

    public List<BoQnaDTO> selectQnaList(BoQnaDTO boQnaDTO) {
        return boQnaMapper.selectQnaList(boQnaDTO);
    }

    public int selectQnaListCnt(BoQnaDTO boQnaDTO) {
        return boQnaMapper.selectQnaListCnt(boQnaDTO);
    }

    public BoQnaDTO selectQnaDetail(String qnaId){
        return boQnaMapper.selectQnaDetail(qnaId);
    }

    public void updateQnaReply(BoQnaDTO boQnaDTO){
       boQnaMapper.updateQnaReply(boQnaDTO);
    }
}
