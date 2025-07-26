package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoQnaDTO extends CommonDTO implements Serializable {
    private int qnaId;
    private String title;
    private String reply;
    private String content;
    private String createId;
    private String replyId;
    private String createDt;
    private String replyDt;
    private String updateDt;
    private String updateId;
    private String custName;
    private String adminNm;
    private String viewCnt;

}
