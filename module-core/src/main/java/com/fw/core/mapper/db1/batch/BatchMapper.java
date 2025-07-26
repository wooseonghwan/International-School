package com.fw.core.mapper.db1.batch;

import com.fw.core.dto.batch.BatchChannelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchMapper {

    List<BatchChannelDTO> selectReserveListForYeogi();
    void updateBatchYnForYeogi(BatchChannelDTO batchChannelDTO);

    List<BatchChannelDTO> selectReserveListForYapen();
    void updateBatchYnForYapen(BatchChannelDTO batchChannelDTO);
}
