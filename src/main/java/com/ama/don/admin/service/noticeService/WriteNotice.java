package com.ama.don.admin.service.noticeService;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

@Service
public class WriteNotice implements NoticeServiceInterface{
    @Override
    public void execute(Model model) {
        Map<String, Object> map=model.asMap();
        MultipartHttpServletRequest mtfRequest = (MultipartHttpServletRequest) map.get("request");
        boolean result = false;

        String noticeTitle = mtfRequest.getParameter("title");
        String noticeContent = mtfRequest.getParameter("content");
    }
}
