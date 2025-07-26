package com.fw.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDTO extends CommonDTO {

    private Long fileId;
    private String fileNm;
    private String fileSaveNm;
    private Long fileSize;
    private String fileType;
    private String ext;
    private String accessIp;
    private String delYn;
    private String createId;
    private LocalDateTime createDt;
    private String updateId;
    private LocalDateTime updateDt;
    private String fileUrl;

    // 업로드를 위한 정보
    private MultipartFile file;
    private String pageType;
    private String ipAddress;

}
