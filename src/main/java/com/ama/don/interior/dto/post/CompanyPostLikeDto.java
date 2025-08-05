package com.ama.don.interior.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostLikeDto {

    private Long companyPostLikeId;
    private Long companyPostId;
    private Long userId;
}
