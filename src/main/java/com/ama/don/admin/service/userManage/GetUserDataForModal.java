package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.UserTotalDataVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetUserDataForModal {

    private final ManageUserIDao manageUserIDao;

    public GetUserDataForModal(ManageUserIDao manageUserIDao) {
        this.manageUserIDao = manageUserIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        String userId = (String) map.get("userId");
        UserTotalDataVO userTotalDataVO = manageUserIDao.getUserByUserId(userId);
        System.out.println("\n>>> userId : "+ userTotalDataVO.getUser_id()+"\n");
        model.addAttribute("userData", userTotalDataVO);
    }
}
