package com.notification.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CheckNewNotificationService {

    private final NotificationGetService notificationGetService;
    private final LastReadAtService lastReadAtService;

    // redis 저장한 lastReadAt(내가 읽은 알림) vs latestUpdatedAt(내가 가진 알림 중 UpdatedAt이 가장 큰 알림)
    // UpdatedAt이 제일 크면 최신순(새로운 알림)
    // UpdatedAt 보다 lastReadAt 더 크면 새로운 알림 없음
    public boolean checkNewNotification(Long userId) {
        Instant latestUpdatedAt = notificationGetService.getLatestUpdatedAt(userId);
        if(latestUpdatedAt == null) {
            return false;
        }

        Instant lastReadAt = lastReadAtService.getLastReadAt(userId);
        if(lastReadAt == null) {
            return true;
        }

        return latestUpdatedAt.isAfter(lastReadAt);
    }
}
