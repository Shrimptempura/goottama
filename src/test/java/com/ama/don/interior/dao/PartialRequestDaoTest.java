package com.ama.don.interior.dao;

import com.ama.don.interior.dto.partialrequest.PartialRequestCreateDto;
import com.ama.don.interior.dto.partialrequest.PartialRequestDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PartialRequestDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    PartialRequestDao partialRequestDao;

    @DisplayName("부분 시공 설문조사 생성 및 조회")
    @Test
    void insertPartialRequestAndResponse() {
        // 업체 생성
        TestCompanyContext context = insertTestCompanyContext();
        Long companyId = context.getCompanyId();

        JoinformDto user = createTestUser("testUser111");
        Long userId = user.getUserId();

        PartialRequestCreateDto dto = new PartialRequestCreateDto();
        dto.setUserId(userId);
        dto.setPartialType("시공 타입 테스트");
        dto.setPartialKind("시공 종류 테스트");
        dto.setPartialArea("시공 공간 테스트");
        dto.setPartialAddr("시공 주소 테스트");
        dto.setPartialFriend("시공 카카오톡 채널 친구");


        // 생성
        int result = partialRequestDao.insertPartialRequest(dto);
        Long partialRequestId = dto.getPartialRequestId();
        assertThat(result).isEqualTo(1);

        PartialRequestDto getData = partialRequestDao.findById(partialRequestId);
        assertThat(getData).isNotNull();
        assertThat(getData.getPartialType()).isEqualTo("시공 타입 테스트");
        assertThat(getData.getStatus()).isEqualTo("WAIT");
    }

}