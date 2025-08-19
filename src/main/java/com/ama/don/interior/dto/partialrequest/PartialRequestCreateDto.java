package com.ama.don.interior.dto.partialrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 부분시공 생성 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PartialRequestCreateDto {

    private Long partialRequestId;
    private Long userId;

    private String partialType;         // 시공 타입
    private String partialKind;         // 시공 종류
    private String partialArea;         // 시공 평수
    private String partialAddr;        // 시공 주소
    private String partialFriend;       // 카카오 채널 추가

    private LocalDateTime createdAt;
    private String status;              // default 'WAIT'

}
