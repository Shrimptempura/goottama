package com.ama.don.interior.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyFollowDto {

    private Long followId;
    private Long companyId;
    private Long userId;
    private LocalDateTime followAt;
}
