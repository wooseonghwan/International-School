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
public class BoAdDTO extends CommonDTO implements Serializable {
    private String historyId;
    private String adId;
    private String projectId;
    private String productId;
    private String historyStatus;
    private String createId;
    private String createDt;
    private String userId;
    private String partnersId;
    private String status;
    private String reportStatus;
    private String itemName;
    private String userType;
    private String name;
    private String partnersName;
    private String statusNm;
    private String platform;
    private String platformNm;
    private String reportStatusNm;

    @Builder.Default
    private String searchText = "";
    private String errorCode;
    private String title;
    private String startDt;
    private String productRequestId;
    private String loginId;
    private String email;
    private String phone;
    private String content;
    private String amount;
}
