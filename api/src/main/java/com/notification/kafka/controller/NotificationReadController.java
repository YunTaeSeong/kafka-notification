package com.notification.kafka.controller;

import com.notification.kafka.response.SetLastReadAtResponse;
import com.notification.kafka.service.LastReadAtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
public class NotificationReadController implements NotificationReadControllerSpec{

    private final LastReadAtService service;

    @Override
    @PutMapping("/{userId}/read")
    public SetLastReadAtResponse setLastReadAt(
            @PathVariable Long userId
    ) {
        Instant instant = service.setLastReadAt(userId);
        return new SetLastReadAtResponse(instant);
    }
}
