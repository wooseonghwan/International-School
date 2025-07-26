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
public class BoMainDTO extends CommonDTO implements Serializable {

    private String today;
    private String estimateCnt;
    private String progressCnt;
    private String qnaCnt;
    private String approvalCnt;
    private String inflowPathName;
    private int inflowCnt;
    private int day;
    private long totalPrice;
    private String label;
    private int count;
    private String title;
    private Long productCnt;

}
