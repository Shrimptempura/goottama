package com.ama.don.interior.controller;

import com.ama.don.interior.service.CompanyReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyReviewController {

    CompanyReviewService companyReviewService;
}
