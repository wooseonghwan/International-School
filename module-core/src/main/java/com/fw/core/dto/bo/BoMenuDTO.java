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
public class BoMenuDTO extends CommonDTO implements Serializable {
    private String menuId;
    private String fileOrder;
    private String fileSeq;
    private String fileName;
}
