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
public class BoChargingDTO extends CommonDTO implements Serializable {
    private String fDate;
    private String cDate;
    private String custName;
    private String custNo;
    private Integer balance;
    private String gbn;
    private String rtn;
    private Integer amnt;
    private String ilno;
    private String snno;
    private String loc;
    private String menuCd;
    private String menuNm;
    private String syncDate;
    private String bigo;
}
