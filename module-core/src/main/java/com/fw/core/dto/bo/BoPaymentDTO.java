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
public class BoPaymentDTO extends CommonDTO implements Serializable {
    private String paymentId;
    private String productType;
    private String estimateId;
    private String adProductId;
    private String productTypeNm;
    private String userId;
    private String name;
    private String status;
    private String statusNm;
    private String price;
    private String payMethod;
    private String payMethodNm;
    private String billFileSeq;
    private String contractFileId;
    private String estimateFileId;
    private String paymentDt;
    private String updateId;
    private String updateDt;
    private String packageName;
    private String productName;

    @Builder.Default
    private String searchText = "";
    private String errorCode;

}
