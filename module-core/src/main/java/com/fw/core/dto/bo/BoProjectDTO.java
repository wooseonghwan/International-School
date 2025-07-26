package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoProjectDTO extends CommonDTO implements Serializable {
    private String projectId;
    private String title;
    private String summary;
    private String content;
    private String productName;
    private String status;
    private String budget;
    private String closeDt;
    private String viewCount;
    private String serviceRequest;
    private String taskRequest;
    private String fileId;
    private String delYn;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;
    private String name;
    private String dDay;
    private String adDate;
    private String adDateName;
    private String adDateEtc;
    private String userIntro;
    private String errorCode;
    private String userId;
    private String loginId;
    private String estimateId;
    private String platform;
    private String platformDetail;
    private String adType;
    private String adminSeq;
    private String fileSaveNm;
    private String fileUrl;
}
