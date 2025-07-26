package com.fw.bo.partner.controller;

import com.fw.bo.partner.service.BoProjectService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoPartnerDTO;
import com.fw.core.dto.bo.BoPaymentDTO;
import com.fw.core.dto.bo.BoProjectDTO;
import com.fw.core.vo.ResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BoProjectController {
    private final BoProjectService boProjectService;
    private final CommonFileService commonFileService;

    @GetMapping("/bo/partner/project-management")
    public String projectManagement(ModelMap model, BoProjectDTO boProjectDTO) {
        if (boProjectDTO.getSearchDate() == null || boProjectDTO.getSearchDate().isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            String defaultSearchDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            boProjectDTO.setSearchDate(defaultSearchDate);
        }

        // 오늘 날짜
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<BoProjectDTO> projectList = boProjectService.selectProjectList(boProjectDTO);
        // D-day 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (BoProjectDTO item : projectList) {
            String closeDtStr = item.getCloseDt();
            if (closeDtStr != null && !closeDtStr.isEmpty()) {
                try {
                    LocalDate closeDate = LocalDate.parse(closeDtStr, formatter);
                    long days = ChronoUnit.DAYS.between(today, closeDate);

                    if (days > 0) {
                        item.setDDay("D-" + days);
                    } else if (days == 0) {
                        item.setDDay("D-DAY");
                    } else if (days < 0) {
                        item.setDDay("마감");
                    }
                } catch (DateTimeParseException e) {
                    item.setDDay("날짜오류");
                }
            } else {
                item.setDDay("미정");
            }
        }
        model.addAttribute("projectList", projectList);
        boProjectDTO.setTotalCount(boProjectService.selectProjectListCnt(boProjectDTO));
        model.addAttribute("searchInfo", boProjectDTO);

        return "/bo/partner/project-management";
    }


    @GetMapping("/bo/partner/project-form")
    public String projectForm(ModelMap model, BoProjectDTO boProjectDTO) {
        model.addAttribute("userList", boProjectService.selectUserList(boProjectDTO));
        return "/bo/partner/project-form";
    }

    @GetMapping("/api/estimate/list")
    @ResponseBody
    public ResponseEntity<?> getEstimateList(@RequestParam("userId") String userId) {
        try {
            List<BoProjectDTO> estimateList = boProjectService.selectEstimateList(userId);
            return ResponseEntity.ok().body(Collections.singletonMap("data", estimateList));
        } catch (Exception e) {
            log.error("견적 리스트 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @GetMapping("/bo/partner/project-detail")
    public String projectDetail(@RequestParam("projectId") String projectId, ModelMap model) {
        BoProjectDTO detail = boProjectService.selectProjectDetail(projectId);
        model.addAttribute("detail", detail);
        model.addAttribute("applyPartnerList", boProjectService.selectApplyPartnerList(projectId));
        return "/bo/partner/project-detail";
    }

    @PostMapping("/bo/partner/update-project")
    public ResponseEntity<ResponseVO> updateProject(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("projectId") String projectId,
            @RequestParam("status") String status,
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("budget") String budget,
            @RequestParam("adDate") String adDate,
            @RequestParam(value = "adDateEtc", required = false) String adDateEtc,
            @RequestParam("closeDt") String closeDt,
            @RequestParam("viewCount") String viewCount,
            @RequestParam("userIntro") String userIntro,
            @RequestParam("content") String content,
            @RequestParam("serviceRequest") String serviceRequest,
            @RequestParam("taskRequest") String taskRequest,
            @RequestParam(value = "fileId", required = false) String fileId, // 기존 fileId
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            Long updatedFileId = fileId != null ? Long.parseLong(fileId) : null;

            if (file != null && !file.isEmpty()) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setFile(file);
                fileDTO.setPageType("PROJECT");
                fileDTO.setIpAddress(request.getRemoteAddr());
                updatedFileId = commonFileService.uploadFile(fileDTO);
            }

            BoProjectDTO dto = new BoProjectDTO();
            dto.setProjectId(projectId);
            dto.setStatus(status);
            dto.setTitle(title);
            dto.setSummary(summary);
            dto.setBudget(budget);
            dto.setAdDate(adDate);
            dto.setAdDateEtc(adDateEtc);
            dto.setCloseDt(closeDt);
            dto.setViewCount(viewCount);
            dto.setUserIntro(userIntro);
            dto.setContent(content);
            dto.setServiceRequest(serviceRequest);
            dto.setTaskRequest(taskRequest);
            dto.setFileId(String.valueOf(updatedFileId));

            boProjectService.updateProject(dto);

        } catch (Exception e) {
            log.error("프로젝트 수정 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
    }


    @PostMapping("/bo/partner/insert-project")
    public ResponseEntity<ResponseVO> insertProject(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("summary") String summary,
            @RequestParam("budget") String budget,
            @RequestParam("adDate") String adDate,
            @RequestParam(value = "adDateEtc", required = false) String adDateEtc,
            @RequestParam("closeDt") String closeDt,
            @RequestParam("viewCount") String viewCount,
            @RequestParam("userId") String userId,
            @RequestParam(value = "estimateId", required = false) String estimateId,
            @RequestParam(value = "userIntro", required = false) String userIntro,
            @RequestParam("content") String content,
            @RequestParam("serviceRequest") String serviceRequest,
            @RequestParam("taskRequest") String taskRequest,
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            // 1. 파일 업로드 처리
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFile(file);
            fileDTO.setPageType("PROJECT");
            fileDTO.setIpAddress(request.getRemoteAddr());

            Long fileId = commonFileService.uploadFile(fileDTO);

            // 2. 프로젝트 DTO 구성
            BoProjectDTO boProjectDTO = new BoProjectDTO();
            boProjectDTO.setTitle(title);
            boProjectDTO.setSummary(summary);
            boProjectDTO.setBudget(budget);
            boProjectDTO.setAdDate(adDate);
            boProjectDTO.setAdDateEtc(adDateEtc);
            boProjectDTO.setCloseDt(closeDt);
            boProjectDTO.setViewCount(viewCount);
            boProjectDTO.setUserId(userId);
            boProjectDTO.setEstimateId(estimateId);
            boProjectDTO.setUserIntro(userIntro);
            boProjectDTO.setContent(content);
            boProjectDTO.setServiceRequest(serviceRequest);
            boProjectDTO.setTaskRequest(taskRequest);
            boProjectDTO.setFileId(String.valueOf(fileId)); // 업로드된 파일 ID

            // 3. 프로젝트 등록
            boProjectService.insertProject(boProjectDTO);

        } catch (Exception e) {
            log.error("프로젝트 등록 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }


    @PostMapping("/bo/partner/delete-project")
    @ResponseBody
    public ResponseEntity<ResponseVO> deleteProject(@RequestBody BoProjectDTO boProjectDTO, HttpServletRequest request, HttpServletResponse response) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            boProjectService.deleteProject(boProjectDTO);
        } catch (Exception e) {
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(boProjectDTO.getErrorCode()).build());
    }
}
