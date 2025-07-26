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
public class BoReportDTO extends CommonDTO implements Serializable {
    private String reportId;
    private String adId;
    private String issueDt;
    private String viewCount;
    private String linkUrl;
    private String keyword;
    private String rank;
    private String category;
    private String etc;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;

}
