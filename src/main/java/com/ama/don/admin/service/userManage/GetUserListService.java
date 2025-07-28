package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.UserSearchVO;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetUserListService {

    private final ManageUserIDao manageUserIDao;

    public GetUserListService(ManageUserIDao manageUserIDao){
        this.manageUserIDao = manageUserIDao;
    }

    public void execute(Model model){
        Map<String, Object> map = model.asMap();
        UserSearchVO userSearchVO = (UserSearchVO) map.get("userSearchVO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<MemberDto> memberDtos;
        int total;

        if (userSearchVO == null ||
                (userSearchVO.getUser_name() == null || userSearchVO.getUser_name().isEmpty()) &&
                (userSearchVO.getUser_nickname() == null || userSearchVO.getUser_nickname().isEmpty()) &&
                (userSearchVO.getUser_addr() == null || userSearchVO.getUser_addr().isEmpty()) &&
                (userSearchVO.getUser_email() == null || userSearchVO.getUser_email().isEmpty()) &&
                (userSearchVO.getUser_gender() == null || userSearchVO.getUser_gender().isEmpty()) &&
                (userSearchVO.getUser_tel() == null || userSearchVO.getUser_tel().isEmpty()) &&
                (userSearchVO.getSearch_zipcode() == null || userSearchVO.getSearch_zipcode().isEmpty()) &&
                (userSearchVO.getCreate_start_date() == null || userSearchVO.getCreate_start_date().isEmpty()) &&
                (userSearchVO.getCreate_end_date() == null || userSearchVO.getCreate_end_date().isEmpty())) {
            total = manageUserIDao.countAllUsers();
            memberDtos = manageUserIDao.getAllUsers(searchVO);
        } else {
            total = manageUserIDao.countSearchUsers(userSearchVO);
            memberDtos = manageUserIDao.searchUsers(searchVO, userSearchVO);
        }

        searchVO.pageCalculate(total);

        for (MemberDto memberDto : memberDtos) {
            Map<String, Object> row = new HashMap<>();
            row.put("userId", memberDto.getUser_id());
            row.put("userName", memberDto.getUser_name());
            row.put("userNickname", memberDto.getUser_nickname());
            row.put("userAddr", memberDto.getUser_addr());
            row.put("userEmail", memberDto.getUser_email());
            row.put("userGender", memberDto.getUser_gender());
            row.put("userTel", memberDto.getUser_tel());
            row.put("userZipcode", memberDto.getUser_zipcode());
            row.put("userCreatedAt", memberDto.getUser_created_at());
            mapList.add(row);
        }

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
