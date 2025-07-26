package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.io.Serializable;

@Getter
@Setter
public class BoUserDTO extends CommonDTO implements Serializable {
    private String custNo;
    private String custName;
    private String cardId;
    private String webId;
    private String webPw;
    private String cDate;
    private String syncDate;
    private String bigo;
}
