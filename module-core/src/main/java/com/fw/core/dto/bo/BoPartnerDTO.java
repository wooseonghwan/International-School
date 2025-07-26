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
public class BoPartnerDTO extends CommonDTO implements Serializable {

    private String adId;
    private String orderNo;
    private String userId;
    private String partnersId;
    private String projectId;
    private String status;
    private String reportStatus;
    private String itemName;
    private String platform;
    private String adType;
    private String adMethod;
    private String amount;
    private String memo;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;

    private String name;
    private String title;
    private String statusName;
    private String reportStatusName;
    private String statusChangeDt;
    private String budget;
    private String bidCnt;
    private String progressCnt;
    private String completeCnt;
    private String billingCnt;
    private String cancelCnt;
    private String totalAmount;
    private String billingAmount;
    private String bidAmount;
    private String progressAmount;
    private String errorCode;
    private String searchText;

}
