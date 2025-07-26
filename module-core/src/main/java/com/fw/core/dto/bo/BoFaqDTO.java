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
public class BoFaqDTO extends CommonDTO implements Serializable {

    private int faqId;
    private String category;
    private String title;
    private String content;
    private String createDt;
    private String updateDt;
    private String createId;
    private String updateId;
    private String useYn;
    private String delYn;
    private String categoryNm;

}
