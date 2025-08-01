package com.fw.bo.homepage.controller;

import com.fw.bo.homepage.service.BoMenuService;
import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.dto.bo.BoMenuDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoMenuController {
    private final CommonFileService commonFileService;
    private final BoMenuService boMenuService;
    @GetMapping({"/bo/reg-menu"})
    String faqList(Model model, BoFaqDTO boFaqDTO){
        return "/bo/homepage/reg-menu";
    }
    @PostMapping("/bo/menu/insert")
    public ResponseEntity<ResponseVO> uploadMenuPdf(
            @RequestParam("monthlyMenu") MultipartFile monthlyMenu,
            @RequestParam("cateringMenu") MultipartFile cateringMenu,
            @RequestParam("fingerFoodMenu") MultipartFile fingerFoodMenu,
            @RequestParam("deliMenu") MultipartFile deliMenu,
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            // 메뉴 종류별 순서를 정의
            LinkedHashMap<String, MultipartFile> fileMap = new LinkedHashMap<>();
            fileMap.put("MONTHLY", monthlyMenu);    // order 1
            fileMap.put("CATERING", cateringMenu);  // order 2
            fileMap.put("FINGER", fingerFoodMenu);  // order 3
            fileMap.put("DELI", deliMenu);          // order 4

            int order = 1;
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile file = entry.getValue();

                if (file != null && !file.isEmpty()) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFile(file);
                    fileDTO.setPageType("MENU-" + entry.getKey());
                    fileDTO.setIpAddress(request.getRemoteAddr());

                    Long fileId = commonFileService.uploadFile(fileDTO);

                    // 메뉴 DTO 저장
                    BoMenuDTO menuDTO = new BoMenuDTO();
                    menuDTO.setFileOrder(String.valueOf(order));
                    menuDTO.setFileSeq(String.valueOf(fileId));

                    boMenuService.insertMenu(menuDTO);

                    log.info("{} menu uploaded: order={}, fileId={}", entry.getKey(), order, fileId);
                }

                order++;
            }

        } catch (Exception e) {
            log.error("메뉴 PDF 업로드 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    @PostMapping("/bo/menu/update")
    public ResponseEntity<ResponseVO> updateMenuPdf(
            @RequestParam("monthlyMenu") MultipartFile monthlyMenu,
            @RequestParam("cateringMenu") MultipartFile cateringMenu,
            @RequestParam("fingerFoodMenu") MultipartFile fingerFoodMenu,
            @RequestParam("deliMenu") MultipartFile deliMenu,
            HttpServletRequest request
    ) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            // 메뉴 종류별 순서를 정의
            LinkedHashMap<String, MultipartFile> fileMap = new LinkedHashMap<>();
            fileMap.put("MONTHLY", monthlyMenu);    // order 1
            fileMap.put("CATERING", cateringMenu);  // order 2
            fileMap.put("FINGER", fingerFoodMenu);  // order 3
            fileMap.put("DELI", deliMenu);          // order 4

            int order = 1;
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile file = entry.getValue();

                if (file != null && !file.isEmpty()) {
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFile(file);
                    fileDTO.setPageType("MENU-" + entry.getKey());
                    fileDTO.setIpAddress(request.getRemoteAddr());

                    Long fileId = commonFileService.uploadFile(fileDTO);

                    // 메뉴 DTO 저장
                    BoMenuDTO menuDTO = new BoMenuDTO();
                    menuDTO.setFileOrder(String.valueOf(order));
                    menuDTO.setFileSeq(String.valueOf(fileId));

                    boMenuService.updateMenu(menuDTO);

                    log.info("{} menu uploaded: order={}, fileId={}", entry.getKey(), order, fileId);
                }

                order++;
            }

        } catch (Exception e) {
            log.error("메뉴 PDF 업로드 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

    @GetMapping("/bo/menu/file-info")
    @ResponseBody
    public List<BoMenuDTO> selectMenuFileName() {
        return boMenuService.selectMenuFileName();
    }

    @DeleteMapping("/bo/menu/file-delete/{fileId}")
    public ResponseEntity<ResponseVO> deleteMenuFile(@PathVariable("fileId") Long fileId) {
        ResponseCode code = ResponseCode.SUCCESS;

        try {
            commonFileService.deleteFileById(fileId);
        } catch (Exception e) {
            log.error("파일 삭제 중 오류", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(code.getHttpStatus())
                .body(ResponseVO.builder(code).build());
    }

}
