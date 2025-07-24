package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.member.dto.JoinformDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestCompanyContext {

    private final JoinformDto user;
    private final CompanyCreateDto detail;
    private final CompanyCreateLocationDto location;
    private final CompanyInsertDto inserted;

    public Long getCompanyId() {
        return inserted.getCompanyId();
    }
}
