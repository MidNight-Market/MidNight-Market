package com.project.www.controller;

import com.project.www.domain.NotificationVO;
import com.project.www.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notification/*")
@Controller
public class NotificationController {
    private final NotificationService nsv;

    @GetMapping("/notifications")
    @ResponseBody
    public List<NotificationVO> getNotifications(HttpSession ses) {
        String username = (String) ses.getAttribute("id");
        if(username != null) {
            List<NotificationVO> notificationList = nsv.getList(username);
            return notificationList;
        }
        return null;
    }


}
