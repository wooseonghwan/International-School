package com.fw.core.dto.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiIntegrateDTO {
    private String id;
    private String pw;
    private String run_type;
    private String yyyymmdd;
    private String room_nm;
}
