package com.ama.don.community.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dto.Review.ReviewWriteDto;
import com.ama.don.community.service.Write_viewService;
import com.ama.don.member.dao.LoginDao;

@Controller
@RequestMapping("/community")
public class Write_viewController {

    @Autowired
    private Write_viewService write_viewService;

    @Autowired
    private LoginDao loginDao;

    @GetMapping("/write_view")
    public String write_form() {
        return "community/write_view";
    }

    @PostMapping("/write")
    @ResponseBody
    public Map<String, Object> write_post(@RequestParam("review_title") String reviewTitle,
                                          @RequestParam("review_content") String reviewContent,
                                          @RequestParam("target_type") String targetTypeStr) {

        Long userId = getCurrentUserId(); // 로그인 사용자 user_id
        if (userId == null) {
            // 비로그인 또는 조회 실패 응답
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("message", "unauthorized");
            return err;
        }

        ReviewWriteDto dto = new ReviewWriteDto();
        dto.setReview_title(reviewTitle);
        dto.setReview_content(reviewContent);
        dto.setTargetType(TargetType.valueOf(targetTypeStr));

        // 서비스 호출
        ReviewWriteDto savedDto = write_viewService.createReviewWithPost(userId, dto);
        Long postId = savedDto.getPost_id();

        // JSON 응답
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("post_id", postId);
        return result;
    }

    // 현재 로그인 사용자의 user_id 조회
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        String loginId = auth.getName();
        return loginDao.findUserIdByLoginId(loginId);
    }
}
