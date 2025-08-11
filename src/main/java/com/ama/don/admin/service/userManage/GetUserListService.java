package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserSearchDTO;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.utils.SearchVO;
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
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = model.asMap();
        UserSearchDTO userSearchDTO = (UserSearchDTO) map.get("userSearchDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<UserTotalDataDTO> memberDtos;
        int total;

        if (userSearchDTO == null ||
                (userSearchDTO.getUser_name() == null || userSearchDTO.getUser_name().isEmpty()) &&
                (userSearchDTO.getUser_nickname() == null || userSearchDTO.getUser_nickname().isEmpty()) &&
                (userSearchDTO.getUser_addr() == null || userSearchDTO.getUser_addr().isEmpty()) &&
                (userSearchDTO.getUser_email() == null || userSearchDTO.getUser_email().isEmpty()) &&
                (userSearchDTO.getUser_gender() == null || userSearchDTO.getUser_gender().isEmpty()) &&
                (userSearchDTO.getUser_tel() == null || userSearchDTO.getUser_tel().isEmpty()) &&
                (userSearchDTO.getUser_zipcode() == null || userSearchDTO.getUser_zipcode().isEmpty()) &&
                (userSearchDTO.getCreate_start_date() == null || userSearchDTO.getCreate_start_date().isEmpty()) &&
                (userSearchDTO.getCreate_end_date() == null || userSearchDTO.getCreate_end_date().isEmpty()) &&
                (userSearchDTO.getUser_status() == null || userSearchDTO.getUser_status().isEmpty())) {
            total = manageUserIDao.countAllUsers();
            memberDtos = manageUserIDao.getAllUsers(searchVO);
        } else {
            total = manageUserIDao.countSearchUsers(userSearchDTO);
            memberDtos = manageUserIDao.searchUsers(searchVO, userSearchDTO);
        }

        searchVO.pageCalculate(total);

        for (UserTotalDataDTO memberDto : memberDtos) {
            Map<String, Object> row = new HashMap<>();
            row.put("userId", memberDto.getUser_id());
            row.put("userName", memberDto.getUser_name());
            row.put("userNickname", memberDto.getUser_nickname());
            row.put("userLoginId", memberDto.getLogin_id());
            row.put("roleId", memberDto.getRoles_id());
            row.put("userAddr", memberDto.getUser_addr());
            row.put("userEmail", memberDto.getUser_email());
            row.put("userGender", memberDto.getUser_gender());
            row.put("userTel", memberDto.getUser_tel());
            row.put("userZipcode", memberDto.getUser_zipcode());
//            if (memberDto.getUser_created_at() != null) {
//                row.put("userCreatedAt", memberDto.getUser_created_at().format(formatter));
//            } else {
//                row.put("userCreatedAt", null);
//            }
            row.put("userCreatedAt", memberDto.getUser_created_at());
            row.put("userSanctionsUntil", memberDto.getUser_sanctions_until());
            row.put("userStatus", memberDto.getUser_status());
            row.put("roleId", memberDto.getRoles_id());
            mapList.add(row);
        }

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
