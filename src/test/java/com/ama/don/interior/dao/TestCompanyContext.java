package com.ama.don.interior.dao;

import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.dto.company.CompanyInsertDto;
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
