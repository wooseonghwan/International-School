package com.fw.bo.content.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fw.bo.content.service.BoContentService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.vo.ResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class BoContentController {

    private final BoContentService boContentService;
    private final CommonFileService commonFileService;

    // 카테고리 리스트
    @GetMapping("/bo/content/category")
    public String category(ModelMap model, BoContentDTO boContentDTO) {
        if (boContentDTO.getSearchDate() == null || boContentDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boContentDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("categoryList", boContentService.selectCategoryList(boContentDTO));
        boContentDTO.setTotalCount(boContentService.selectCategoryListCnt(boContentDTO));
        model.addAttribute("searchInfo", boContentDTO);

        return "/bo/content/category";
    }

    // 지역관리
    @GetMapping("/bo/content/package")
    public String area(ModelMap model, BoContentDTO boContentDTO){
        if (boContentDTO.getSearchDate() == null || boContentDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boContentDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("packageList", boContentService.selectPackageList(boContentDTO));
        boContentDTO.setTotalCount(boContentService.selectPackageListCnt(boContentDTO));
        model.addAttribute("searchInfo", boContentDTO);
        return "/bo/content/package";
    }

//    // 카테고리 등록
//    @PostMapping("/bo/content/insert-category")
//    @ResponseBody
//    public ResponseEntity<ResponseVO> insertCategory(@RequestPart(value = "jsonData") BoContentDTO boContentDTO,
//                                                      @RequestPart(value = "productFiles", required = false) MultipartFile[] productFiles, HttpServletRequest request, HttpServletResponse response) {
//        ResponseCode code = ResponseCode.SUCCESS;
//        try {
//            boContentDTO.setProductFiles(productFiles);
//            boContentService.insertCategory(boContentDTO);
//        } catch (Exception e) {
//            log.error("error", e);
//            code = ResponseCode.INTERNAL_SERVER_ERROR;
//        }
//        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boContentDTO.getErrorCode()).build());
//    }

    // 카테고리 등록 (파일 x)
    @PostMapping("/bo/content/insert-category")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertCategory(@RequestBody BoContentDTO boContentDTO) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentService.insertCategory(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boContentDTO.getErrorCode()).build());
    }

    // 카테고리 상세
    @GetMapping("/bo/content/category-detail")
    public ResponseEntity<ResponseVO> categoryDetail(ModelMap model, BoContentDTO boContentDTO, @RequestParam(name="categoryId") String categoryId) {
        // 카테고리 상세 정보 가져오기
        BoContentDTO detail = boContentService.selectCategoryDetail(categoryId);

        // 파일 정보가 있는 경우 가져오기
//        List<FileDTO> fileList = null;
//        if (StringUtils.isNotEmpty(detail.getFileSeq())) {
//            fileList = boContentService.selectProductFileList(detail.getFileSeq());
//        }

        // 응답 데이터 구성
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("detail", detail);  // 'detail' 키로 detail 객체 넣기
//        responseData.put("file", fileList);  // 'file' 키로 파일 목록 추가

        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code)
                        .data(responseData)  // 'data' 필드에 전체 데이터 포함
                        .build());
    }

    // 카테고리 수정
    @PostMapping("/bo/content/update-category")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateCategory(@RequestPart(value = "jsonData") BoContentDTO boContentDTO,
//                                                    @RequestPart(value = "productFiles", required = false) MultipartFile[] productFiles,
                                                      HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
//            boContentDTO.setProductFiles(productFiles);
            boContentService.updateCategory(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boContentDTO.getErrorCode()).build());
    }

    // 카테고리 삭제
    @PostMapping("/bo/content/delete-category")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteCategory(BoContentDTO boContentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentService.deleteCategory(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    // 카테고리 엑셀 다운로드
    @GetMapping("/bo/content/category/excel-download")
    @ResponseBody
    public void downloadCategoryExcel(HttpServletResponse response, BoContentDTO boContentDTO) throws IOException {
        // 1. 데이터 조회
        List<BoContentDTO> categoryList = boContentService.selectCategoryListForExcel(boContentDTO);

        // 2. 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Category List");

        // 3. 헤더 행 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("사용여부");
        headerRow.createCell(1).setCellValue("카테고리명");
        headerRow.createCell(2).setCellValue("서브 카테고리명");
        headerRow.createCell(3).setCellValue("등록일자");
        headerRow.createCell(4).setCellValue("상태");

        // 4. 데이터 행 생성
        int rowNum = 1;
        for (BoContentDTO category : categoryList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getUseYnNm());
            row.createCell(1).setCellValue(category.getCategoryName());
            row.createCell(2).setCellValue(category.getSubCategoryName());
            row.createCell(3).setCellValue(category.getCreateDt());
            row.createCell(4).setCellValue(category.getUseYnNm());
        }

        // 5. 파일 다운로드 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=카테고리_리스트.xlsx");

        // 6. 엑셀 파일 전송
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // 광고상품 목록
    @GetMapping("/bo/content/ad-product")
    public String adProduct(ModelMap model, BoContentDTO boContentDTO){
        if (boContentDTO.getSearchDate() == null || boContentDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boContentDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("adProductList", boContentService.selectAdProductList(boContentDTO));
        boContentDTO.setTotalCount(boContentService.selectAdProductListCnt(boContentDTO));
        model.addAttribute("searchInfo", boContentDTO);
        return "/bo/content/ad-product";
    }

    // 광고 상품 상세
    @GetMapping("/bo/content/ad-product-detail")
    public String adProductDetail(BoContentDTO boContentDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        BoContentDTO detail = boContentService.selectAdProductDetail(boContentDTO);
        model.addAttribute("detail", detail);

//        if ( StringUtils.isNotEmpty(detail.getFileId()) ) {
//            model.addAttribute("file", boContentService.selectProductFileList(detail.getFileId()));
//        }
        return "/bo/content/ad-product-detail";
    }

    // 광고상품 등록 페이지
    @GetMapping("/bo/content/ad-product-form")
    public String adProductForm(ModelMap model, BoContentDTO boContentDTO){
        return "/bo/content/ad-product-form";
    }
    // 광고 상품 등록
    @PostMapping("/bo/content/insert-ad-product")
    public ResponseEntity<ResponseVO> insertAdProduct(
            @RequestParam("thumbnailImage") MultipartFile file,
            @RequestParam("media") String media,
            @RequestParam("mediaDetail") String mediaDetail,
            @RequestParam("title") String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "mainContent", required = false) String mainContent,
            @RequestParam(value = "amountContent", required = false) String amountContent,
            @RequestParam(value = "etcContent", required = false) String etcContent,
            @RequestParam("amount") String amount,
            @RequestParam("workDt") String workDt,
            @RequestParam(value = "productSubContents", required = false) String productSubContentsJson,
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            // 1. 대표 이미지 업로드 처리
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFile(file);
            fileDTO.setPageType("AD-PRODUCT");
            fileDTO.setIpAddress(request.getRemoteAddr());

            Long fileId = commonFileService.uploadFile(fileDTO);

            // 2. DTO 생성
            BoContentDTO dto = new BoContentDTO();
            dto.setMedia(media);
            dto.setMediaDetail(mediaDetail);
            dto.setTitle(title);
            dto.setContent(content);
            dto.setMainContent(mainContent);
            dto.setAmountContent(amountContent);
            dto.setEtcContent(etcContent);
            dto.setAmount(amount);
            dto.setWorkDt(workDt);
            dto.setFileId(String.valueOf(fileId));

            // 3. 부가 설명 리스트 (JSON -> List<String> 변환)
            if (StringUtils.isNotBlank(productSubContentsJson)) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> productSubContents = objectMapper.readValue(
                        productSubContentsJson,
                        new TypeReference<List<String>>() {}
                );
                dto.setProductSubContents(productSubContents);
            }

            // 4. 저장 서비스 호출
            boContentService.insertAdProduct(dto);

        } catch (Exception e) {
            log.error("ad-product 등록 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    // 광고 상품 수정
    @PostMapping("/bo/content/update-ad-product")
    @ResponseBody
    public ResponseEntity<ResponseVO> updateAdProduct(
            @RequestParam("productId") String productId,
            @RequestParam(value = "thumbnailImage", required = false) MultipartFile file,
            @RequestParam(value = "originFileId", required = false) String originFileId,
            @RequestParam("media") String media,
            @RequestParam("mediaDetail") String mediaDetail,
            @RequestParam("title") String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "mainContent", required = false) String mainContent,
            @RequestParam(value = "amountContent", required = false) String amountContent,
            @RequestParam(value = "etcContent", required = false) String etcContent,
            @RequestParam("amount") String amount,
            @RequestParam("workDt") String workDt,
            @RequestParam(value = "productSubContents", required = false) String productSubContentsJson,
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            Long fileId = null;

            // 새 이미지가 선택된 경우 업로드
            if (file != null && !file.isEmpty()) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setFile(file);
                fileDTO.setPageType("AD-PRODUCT");
                fileDTO.setIpAddress(request.getRemoteAddr());

                fileId = commonFileService.uploadFile(fileDTO);
            } else if (StringUtils.isNotBlank(originFileId)) {
                fileId = Long.parseLong(originFileId); // 기존 이미지 유지
            }

            BoContentDTO dto = new BoContentDTO();
            dto.setProductId(productId);
            dto.setMedia(media);
            dto.setMediaDetail(mediaDetail);
            dto.setTitle(title);
            dto.setContent(content);
            dto.setMainContent(mainContent);
            dto.setAmountContent(amountContent);
            dto.setEtcContent(etcContent);
            dto.setAmount(amount);
            dto.setWorkDt(workDt);
            dto.setFileId(String.valueOf(fileId));

            if (StringUtils.isNotBlank(productSubContentsJson)) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> productSubContents = objectMapper.readValue(
                        productSubContentsJson,
                        new TypeReference<List<String>>() {}
                );
                dto.setProductSubContents(productSubContents);
            }
            boContentService.updateAdProduct(dto);

        } catch (Exception e) {
            log.error("ad-product 등록 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    // 광고 상품 삭제
    @PostMapping("/bo/content/delete-ad-product")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteAdProduct(BoContentDTO boContentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentService.deleteAdProduct(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    // 패키지 상세
    @GetMapping("/bo/content/package-detail")
    public String packageDetail(BoContentDTO boContentDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        BoContentDTO detail = boContentService.selectPackageDetail(boContentDTO);
        model.addAttribute("detail", detail);
        return "/bo/content/package-detail";
    }

    // 패키지 등록
    @PostMapping("/bo/content/insert-package")
    @ResponseBody
    public ResponseEntity<ResponseVO> insertPackage(@RequestPart(value = "jsonData") BoContentDTO boContentDTO,
                                                      @RequestPart(value = "productFiles", required = false) MultipartFile[] productFiles, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentDTO.setProductFiles(productFiles);
            boContentService.insertPackage(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boContentDTO.getErrorCode()).build());
    }

    // 패키지 수정
    @PostMapping("/bo/content/update-package")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePackage(@RequestPart(value = "jsonData") BoContentDTO boContentDTO,
                                                    @RequestPart(value = "productFiles", required = false) MultipartFile[] productFiles, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentDTO.setProductFiles(productFiles);
            boContentService.updatePackage(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boContentDTO.getErrorCode()).build());
    }
    // 패키지 삭제
    @PostMapping("/bo/content/delete-package")
    @ResponseBody
    public ResponseEntity<ResponseVO> deletePackage(BoContentDTO boContentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boContentService.deletePackage(boContentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }

    // 패키지 등록 페이지
    @GetMapping("/bo/content/package-form")
    public String packageForm(ModelMap model, BoContentDTO boContentDTO){
        return "/bo/content/package-form";
    }
}
