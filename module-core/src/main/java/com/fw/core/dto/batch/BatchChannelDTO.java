package com.fw.core.dto.batch;

import com.fw.core.dto.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchChannelDTO extends CommonDTO {

    private String ygSeq;
    private String ypSeq;
    private String reserveStatus;
    private String reserveNo;
    private String reserveNm;
    private String reservePhone;
    private String roomNm;
    private String lodgment;
    private String checkInDt;
    private String checkOutDt;
    private String reservePrice;
    private String channelNm;
    private String paymentDt;
    private String cancelDt;
    private String batchYn;
    private String platformSeq;
    private String userId;
    private String depositPrice;

}
