package com.notification.kafka.service;

import com.notification.kafka.notification.Notification;
import com.notification.kafka.repository.NotificationRepository;
import com.notification.kafka.service.dto.GetUserNotificationsByPivotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationListService {

    private static final int PAGE_SIZE = 20;

    private final NotificationRepository notificationRepository;

    // 목록 조회 Pivot 방식
    public GetUserNotificationsByPivotResult getUserNotificationsByPivot(Long userId, Instant occurredAt) {
        Slice<Notification> result;
        if(occurredAt == null) {
            result = notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0, PAGE_SIZE));
        } else {
            result = notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationsByPivotResult(
                result.toList(),
                result.hasNext()
        );
    }
}
