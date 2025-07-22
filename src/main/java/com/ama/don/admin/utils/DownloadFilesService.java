package com.ama.don.admin.utils;

import com.ama.don.admin.service.noticeService.NoticeServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class DownloadFilesService implements NoticeServiceInterface {

    @Override
    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        HttpServletResponse response = (HttpServletResponse) map.get("response");

    }
}
