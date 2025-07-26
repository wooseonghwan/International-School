package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoContentMapper {
    // 카테고리 관련
    List<BoContentDTO> selectCategoryList(BoContentDTO boContentDTO);
    int selectCategoryListCnt(BoContentDTO boContentDTO);
    void insertCategory(BoContentDTO boContentDTO);
    BoContentDTO selectCategoryDetail(String categoryId);
    void updateCategory(BoContentDTO boContentDTO);
    void deleteCategory(BoContentDTO boContentDTO);
    List<BoContentDTO> selectCategoryListForExcel(BoContentDTO boContentDTO);


    // 광고 상품 관련
    List<BoContentDTO> selectAdProductList(BoContentDTO boContentDTO);
    int selectAdProductListCnt(BoContentDTO boContentDTO);
    BoContentDTO selectAdProductDetail(BoContentDTO boContentDTO);
    List<String> selectProductSubContents(String adProductSeq);

    void insertAdProduct(BoContentDTO boContentDTO);
    void insertProductSubContent(BoContentDTO boContentDTO);
    void updateAdProduct(BoContentDTO boContentDTO);
    void deleteProductSubContent(String adProductSeq);
    void deleteAdProduct(BoContentDTO boContentDTO);


    // 패키지 관련
    List<BoContentDTO> selectPackageList(BoContentDTO boContentDTO);
    int selectPackageListCnt(BoContentDTO boContentDTO);
    BoContentDTO selectPackageDetail(BoContentDTO boContentDTO);
    void insertPackage(BoContentDTO boContentDTO);
    void updatePackage(BoContentDTO boContentDTO);
    void deletePackage(BoContentDTO boContentDTO);
}
