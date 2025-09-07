package com.notification.kafka.service;

import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.follow.FollowNotification;
import com.notification.kafka.like.LikeNotification;
import com.notification.kafka.service.converter.CommentUserNotificationConverter;
import com.notification.kafka.service.converter.FollowUserNotificationConverter;
import com.notification.kafka.service.converter.LikeUserNotificationConverter;
import com.notification.kafka.service.dto.ConvertedNotification;
import com.notification.kafka.service.dto.GetUserNotificationResult;
import com.notification.kafka.service.dto.GetUserNotificationsByPivotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserNotificationService {
    private final NotificationListService listService;
    private final CommentUserNotificationConverter commentConverter;
    private final LikeUserNotificationConverter likeConverter;
    private final FollowUserNotificationConverter followConverter;

    public GetUserNotificationResult getUserNotificationByPivot(Long userId, Instant pivot) {
        // 알림 목록 조회
        GetUserNotificationsByPivotResult result = listService.getUserNotificationsByPivot(userId, pivot);

        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                            case COMMENT -> commentConverter.convert((CommentNotification) notification);
                            case LIKE -> likeConverter.convert((LikeNotification) notification);
                            case FOLLOW -> followConverter.convert((FollowNotification) notification);
        })
        .toList();

        // 알림 목록 순회하면서 알림 -> 사용자 알림 으로 변환
        return new GetUserNotificationResult(
                convertedNotifications,
                result.isHasNext()
        );

    }
}
