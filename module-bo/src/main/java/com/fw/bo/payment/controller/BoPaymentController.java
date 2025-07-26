package com.fw.bo.payment.controller;

import com.fw.bo.content.service.BoContentService;
import com.fw.bo.payment.service.BoPaymentService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.bo.BoContentDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
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
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BoPaymentController {
    private final BoPaymentService boPaymentService;

    @GetMapping("/bo/payment/payment-history")
    public String paymentHistory(ModelMap model, BoPaymentDTO boPaymentDTO) {
        if (boPaymentDTO.getSearchDate() == null || boPaymentDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boPaymentDTO.setSearchDate(defaultSearchDate);
        }
        model.addAttribute("paymentList", boPaymentService.selectPaymentList(boPaymentDTO));
        boPaymentDTO.setTotalCount(boPaymentService.selectPaymentListCnt(boPaymentDTO));
        model.addAttribute("searchInfo", boPaymentDTO);
        return "/bo/payment/payment-history";
    }
    // 결제내역 상세
    @GetMapping("/bo/payment/payment-history-detail")
    public String paymentDetail(BoPaymentDTO boPaymentDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        BoPaymentDTO detail = boPaymentService.selectPaymentDetail(boPaymentDTO);
        model.addAttribute("detail", detail);
        return "/bo/payment/payment-history-detail";
    }
    // 광고 상품 수정
    @PostMapping("/bo/payment/update-payment")
    @ResponseBody
    public ResponseEntity<ResponseVO> updatePayment(@RequestBody BoPaymentDTO boPaymentDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boPaymentService.updatePayment(boPaymentDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boPaymentDTO.getErrorCode()).build());
    }
    // 결제 내역 엑셀 다운로드
    @GetMapping("/bo/payment/payment-history/excel-download")
    @ResponseBody
    public void downloadPaymentExcel(
            HttpServletResponse response,
            BoPaymentDTO boPaymentDTO) throws IOException {
        // 1. 필터링 조건에 맞는 데이터 조회
        List<BoPaymentDTO> paymentList = boPaymentService.selectPaymentList(boPaymentDTO);

        // 2. 엑셀 파일 생성 및 데이터 추가 (이전 코드와 동일)
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Payment List");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("결제 ID");
        headerRow.createCell(1).setCellValue("결제일자");
        headerRow.createCell(2).setCellValue("고객명");
        headerRow.createCell(3).setCellValue("상품종류");
        headerRow.createCell(4).setCellValue("품목명");
        headerRow.createCell(5).setCellValue("결제금액");
        headerRow.createCell(6).setCellValue("결제상태");
        headerRow.createCell(7).setCellValue("결제수단");

        int rowNum = 1;
        for (BoPaymentDTO payment : paymentList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(payment.getPaymentId());
            row.createCell(1).setCellValue(payment.getPaymentDt());
            row.createCell(2).setCellValue(payment.getName());
            row.createCell(3).setCellValue(payment.getProductTypeNm());

            if ("P".equals(payment.getProductType())) {
                row.createCell(4).setCellValue(payment.getPackageName());
            } else if ("A".equals(payment.getProductType()) || "S".equals(payment.getProductType())) {
                row.createCell(4).setCellValue(payment.getProductName());
            }

            try {
                double price = Double.parseDouble(payment.getPrice().replace(",", ""));
                row.createCell(5).setCellValue(price);
            } catch (NumberFormatException e) {
                row.createCell(5).setCellValue(payment.getPrice());
            }

            row.createCell(6).setCellValue(payment.getStatusNm());
            row.createCell(7).setCellValue(payment.getPayMethodNm());
        }

        // 5. 파일 다운로드 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=결제_리스트.xlsx");

        // 6. 엑셀 파일 전송
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
