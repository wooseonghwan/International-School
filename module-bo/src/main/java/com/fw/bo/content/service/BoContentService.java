package com.fw.bo.content.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.mapper.db1.bo.BoContentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class BoContentService {
    private final BoContentMapper boContentMapper;

    // 카테고리 리스트
    public List<BoContentDTO> selectCategoryList(BoContentDTO boContentDTO) {
        return boContentMapper.selectCategoryList(boContentDTO);
    }
    // 카테고리 리스트 count
    public int selectCategoryListCnt(BoContentDTO boContentDTO) {
        return boContentMapper.selectCategoryListCnt(boContentDTO);
    }
    // 카테고리 등록
    @Transactional
    public void insertCategory(BoContentDTO boContentDTO) throws Exception {
        boContentMapper.insertCategory(boContentDTO);
    }

    // 카테고리 상세
    public BoContentDTO selectCategoryDetail(String categoryId){
        BoContentDTO boContentDTO = boContentMapper.selectCategoryDetail(categoryId);
        return boContentDTO;
    }

    // 카테고리 수정
    public void updateCategory(BoContentDTO boContentDTO) throws Exception{
        boContentMapper.updateCategory(boContentDTO);
    }
    // 카테고리 삭제
    public void deleteCategory(BoContentDTO boContentDTO) {
       // commonFileService.updateFileDetail(boContentDTO.getFileSeq());
        boContentMapper.deleteCategory(boContentDTO);
    }
    // 카테고리 리스트 (엑셀)
    public List<BoContentDTO> selectCategoryListForExcel(BoContentDTO boContentDTO) {
        return boContentMapper.selectCategoryListForExcel(boContentDTO);
    }
    // 광고 상품 리스트
    public List<BoContentDTO> selectAdProductList(BoContentDTO boContentDTO) {
        return boContentMapper.selectAdProductList(boContentDTO);
    }
    // 광고 상품 리스트 count
    public int selectAdProductListCnt(BoContentDTO boContentDTO) {
        return boContentMapper.selectAdProductListCnt(boContentDTO);
    }
    // 패키지 리스트
    public List<BoContentDTO> selectPackageList(BoContentDTO boContentDTO) {
        return boContentMapper.selectPackageList(boContentDTO);
    }
    // 패키지 리스트 count
    public int selectPackageListCnt(BoContentDTO boContentDTO) {
        return boContentMapper.selectPackageListCnt(boContentDTO);
    }
   // 광고 상품 상세
   public BoContentDTO selectAdProductDetail(BoContentDTO boContentDTO) {

       // 기본 상품 정보 조회
       BoContentDTO detail = boContentMapper.selectAdProductDetail(boContentDTO);

       if (detail != null) {
           // 부가 설명 리스트 조회 후 DTO에 추가
           List<String> subContents = boContentMapper.selectProductSubContents(detail.getProductId());
           detail.setProductSubContents(subContents);
       }
       return detail;
   }

    // 광고 상품 등록
    @Transactional
    public void insertAdProduct(BoContentDTO boContentDTO) throws Exception {
        boContentMapper.insertAdProduct(boContentDTO);

        if (boContentDTO.getProductSubContents() != null && !boContentDTO.getProductSubContents().isEmpty()) {
            for (String productSubContent : boContentDTO.getProductSubContents()) {
                boContentDTO.setProductSubContent(productSubContent);
                boContentMapper.insertProductSubContent(boContentDTO);
            }
        }
    }

    // 광고 상품 수정
    @Transactional
    public void updateAdProduct(BoContentDTO boContentDTO) throws Exception {
        boContentMapper.updateAdProduct(boContentDTO);
        // 기존 부가 설명 삭제 후 새로 삽입
        boContentMapper.deleteProductSubContent(boContentDTO.getProductId());
        if (boContentDTO.getProductSubContents() != null && !boContentDTO.getProductSubContents().isEmpty()) {
            for (String subContent : boContentDTO.getProductSubContents()) {
                boContentDTO.setProductSubContent(subContent);
                boContentMapper.insertProductSubContent(boContentDTO);
            }
        }
    }
    // 광고 상품 삭제
    public void deleteAdProduct(BoContentDTO boContentDTO) {
        boContentMapper.deleteAdProduct(boContentDTO);
    }
    @Transactional
    public void insertPackage(BoContentDTO boContentDTO) throws Exception {
        boContentMapper.insertPackage(boContentDTO);
    }
    // 패키지 상세
    public BoContentDTO selectPackageDetail(BoContentDTO boContentDTO) {
        return boContentMapper.selectPackageDetail(boContentDTO);
    }
    // 패키지 수정
    @Transactional
    public void updatePackage(BoContentDTO boContentDTO) throws Exception {
        boContentMapper.updatePackage(boContentDTO);
    }
    public void deletePackage(BoContentDTO boContentDTO) {
        boContentMapper.deletePackage(boContentDTO);
    }
}
