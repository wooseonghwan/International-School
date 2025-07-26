package com.fw.core.util;

import com.fw.core.dto.batch.BatchChannelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationUtil {

    public static boolean isValidChannel(BatchChannelDTO batchChannelDTO) {
        // FIXME :: 추후 유효성검사 필요
        return true;
    }

}
