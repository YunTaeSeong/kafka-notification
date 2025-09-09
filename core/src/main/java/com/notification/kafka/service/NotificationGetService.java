package com.notification.kafka.service;

import com.notification.kafka.notification.Notification;
import com.notification.kafka.repository.NotificationRepository;
import com.notification.kafka.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGetService {

    private final NotificationRepository repository;

    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) {
        return repository.findByTypeAndCommentId(type, commentId);
    }

    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return repository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndFollowerId(NotificationType type, Long userId, Long followerId) {
        return repository.findByTypeAndUserIdAndFollowerId(type, userId, followerId);
    }

    public Instant getLatestUpdatedAt(Long userId) {
        Optional<Notification> notification = repository.findFirstByUserIdOrderByLastUpdatedAtDesc(userId);

        return notification.map(Notification::getLastUpdatedAt).orElse(null);
    }
}
