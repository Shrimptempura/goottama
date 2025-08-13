package com.ama.don.interior.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyPostDetailView {

    private CompanyPostDetailSplitDto post;
    private CompanyPostBasicInfoDto company;
}
