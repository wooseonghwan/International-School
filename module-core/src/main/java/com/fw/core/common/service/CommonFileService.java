package com.fw.core.common.service;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.FileDTO;
import com.fw.core.mapper.db1.common.CommonFileMapper;
import com.fw.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonFileService {

    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    private final CommonFileMapper commonFileMapper;

    public InputStreamResource loadFile(FileDTO fileDTO) {
        try {
            Path filePath = Paths.get(fileDTO.getFileSaveNm());

            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new RuntimeException(ResponseCode.FILE_NOT_FOUND.getStatusId());
            }

            return new InputStreamResource(Files.newInputStream(filePath));

        } catch (IOException e) {
            throw new RuntimeException(ResponseCode.INTERNAL_SERVER_ERROR.getStatusId());
        }
    }


    @Transactional
    public Long uploadFile(FileDTO fileDTO) {
        try {
            MultipartFile file = fileDTO.getFile();
            String pageType = fileDTO.getPageType();

            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("파일이 존재하지 않습니다.");
            }

            // 저장 디렉토리: /www/upload/PROFILE/
            String fileSaveDir = FILE_UPLOAD_PATH + pageType + File.separator;
            Path uploadPath = Paths.get(fileSaveDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 저장 파일명: UUID + 확장자
            String originalName = file.getOriginalFilename();
            String fileExtension = "";

            int lastDotIndex = originalName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                fileExtension = originalName.substring(lastDotIndex); // 예: .png
            }

            String fileSaveName = UUID.randomUUID() + fileExtension;
            Path fullPath = uploadPath.resolve(fileSaveName);

            // 실제 파일 저장
            Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

            String fileSaveNm = (FILE_UPLOAD_PATH + pageType + File.separator + fileSaveName)
                    .replace("/", "\\"); // 윈도우형 구분자로 변환

            // DTO 정보 세팅
            fileDTO.setFileNm(originalName);
            fileDTO.setFileSaveNm(fileSaveNm); // DB에 저장될 경로
            fileDTO.setFileUrl("/upload/" + pageType + "/" + fileSaveName); // 웹 접근 URL
            fileDTO.setFileSize(file.getSize());
            fileDTO.setFileType(file.getContentType());
            fileDTO.setExt(fileExtension.replace(".", ""));
            fileDTO.setAccessIp(fileDTO.getAccessIp());

            // DB 등록
            commonFileMapper.insertFile(fileDTO);
            return fileDTO.getFileId();

        } catch (IOException e) {
            log.error("File Upload Error", e);
            throw new RuntimeException(ResponseCode.FILE_UPLOAD_FAIL.getStatusId());
        }
    }

    @Transactional
    public void deleteFileById(Long fileId) {
        // DB에서 파일 정보 조회
        FileDTO fileDTO = commonFileMapper.selectFileById(fileId);
        if (fileDTO == null) {
            throw new RuntimeException("해당 파일을 찾을 수 없습니다.");
        }

        // 실제 파일 삭제 시도
        try {
            Path path = Paths.get(fileDTO.getFileSaveNm());
            Files.deleteIfExists(path); // 파일 존재할 경우 삭제
        } catch (IOException e) {
            log.warn("파일 시스템에서 파일 삭제 실패: {}", fileDTO.getFileSaveNm(), e);
        }

        // DB에서 메뉴(T_MENU)와 파일(T_FILE) 정보 삭제
        commonFileMapper.deleteMenuByFileSeq(fileId);
        commonFileMapper.deleteFileById(fileId);
    }
}
