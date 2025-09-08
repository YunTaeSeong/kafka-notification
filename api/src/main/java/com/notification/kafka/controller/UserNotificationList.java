package com.notification.kafka.controller;

import com.notification.kafka.response.UserNotificationListResponse;
import com.notification.kafka.service.GetUserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notification")
public class UserNotificationList implements UserNotificationListControllerSpec{

    private final GetUserNotificationService getUserNotificationService;

    @Override
    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "pivot", required = false) Instant pivot
    ) {
        return UserNotificationListResponse.of(
                getUserNotificationService.getUserNotificationByPivot(userId, pivot)
        );
    }
}
