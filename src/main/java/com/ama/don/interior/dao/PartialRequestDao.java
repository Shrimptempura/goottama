package com.ama.don.interior.dao;

import com.ama.don.interior.dto.partialrequest.PartialRequestCreateDto;
import com.ama.don.interior.dto.partialrequest.PartialRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PartialRequestDao {

    // 부분시공 설문조사 생성
    int insertPartialRequest(PartialRequestCreateDto dto);

    // 전체 조회
    PartialRequestDto findById(Long partialRequestId);


}
