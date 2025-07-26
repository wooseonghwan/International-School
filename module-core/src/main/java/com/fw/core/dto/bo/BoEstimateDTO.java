package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoEstimateDTO extends CommonDTO {
    private String estimateId;
    private String userId;
    private String categoryId;
    private String locationId;
    private String platform;
    private String platformDetail;
    private String adType;
    private String adTypeEtc;
    private String adTime;
    private String adTimeEtc;
    private String keywordId;
    private String keywordEtc;
    private String estimateAmount;
    private String status;
    private String memo;
    private String createDt;
    private String createId;
    private String updateDt;
    private String updateId;
    private String successDt;
    private String name;
    private String statusNm;
    @Builder.Default
    private String searchText = "";
    private String categoryName;
    private String subCategoryName;
    private String city;
    private String district;
    private String town;
    private String keywordName;
    private String email;
    private String phone;
    private String loginId;
    private String businessNumber;
    private String errorCode;
    private String beforeEstimateAmount;
    private String updateUserNm;
    private String updateNm;
}
