package com.notification.kafka.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGetService {

    private final NotificationRepository repository;

    public Optional<Notification> getNotification(NotificationType type, Long commentId) {
        return repository.findByTypeAndCommentId(type, commentId);
    }

}
