package com.ama.don.admin.dao;

import com.ama.don.admin.dto.SanctionsDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface SanctionsIDao {
    ArrayList<SanctionsDto> getAllSanctions();
    SanctionsDto getSanctionsById(String sanctionsId);
    SanctionsDto getSanctionsByUserId(String userId);
    SanctionsDto getSanctionsByTypes(String sanctionType);
    ArrayList<SanctionsDto> getSanctionsByStartDateRange(Timestamp start, Timestamp end);
    ArrayList<SanctionsDto> getSanctionsByEndDateRange(Timestamp start, Timestamp end);
    ArrayList<SanctionsDto> getSanctionsByReason(String sanctionReason);
    ArrayList<SanctionsDto> getSanctionsByAdmin(String adminId);
    ArrayList<SanctionsDto> getSanctionsByCreatedDateRange(Timestamp createdAt);
    ArrayList<SanctionsDto> getSanctionsByDuration(int a, int b);
    boolean makeSanction(String userId, String sanctionType, Timestamp start, Timestamp end, String reason, String adminId, Timestamp createdAt);
    boolean modifySanction(String userId, String sanctionType, Timestamp start, Timestamp end, String reason, String adminId, Timestamp createdAt);
    boolean deleteSanction(String userId);
}
