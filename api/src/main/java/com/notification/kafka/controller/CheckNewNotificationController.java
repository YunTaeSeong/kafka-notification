package com.notification.kafka.controller;

import com.notification.kafka.response.CheckNewNotificationResponse;
import com.notification.kafka.service.CheckNewNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
public class CheckNewNotificationController implements CheckNewNotificationControllerSpec{

    private final CheckNewNotificationService service;

    @Override
    @GetMapping("/{userId}/new")
    public CheckNewNotificationResponse checkNew(
            @PathVariable("userId") Long userId
    ) {
        boolean hasNew = service.checkNewNotification(userId);
        return new CheckNewNotificationResponse(hasNew);
    }
}
