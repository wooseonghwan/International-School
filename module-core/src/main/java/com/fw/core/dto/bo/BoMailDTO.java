package com.fw.core.dto.bo;

import lombok.Data;

@Data
public class BoMailDTO {
    private int id;
    private String name;
    private String email;
    private String message;
    private String authCode;
}
