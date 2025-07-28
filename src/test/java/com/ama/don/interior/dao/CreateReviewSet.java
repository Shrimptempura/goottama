package com.ama.don.interior.dao;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewSet {

    private ReviewDto commonReviewDto;
    private CompanyReviewCreateDto companyReviewDto;
}
