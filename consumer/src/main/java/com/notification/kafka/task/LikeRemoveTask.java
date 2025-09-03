package com.notification.kafka.task;

import com.notification.kafka.event.LikeEvent;
import com.notification.kafka.like.LikeNotification;
import com.notification.kafka.notification.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeRemoveTask {

    private final NotificationGetService getService;
    private final NotificationRemoveService removeService;
    private final NotificationSaveService saveService;

    public void processEvent(LikeEvent event) {
        Optional<Notification> optionalNotification = getService.getNotificationByTypeAndPostId(NotificationType.LIKE, event.getPostId());
        if(optionalNotification.isEmpty()) {
            log.error("No notification with postId {}", event.getPostId());
            return;
        }

        // liker 에 event.userId 제거 -> 1. liker 비어 있으면 알림 삭제 2. 남아 있으면 알림 업데이트
        LikeNotification notification = (LikeNotification) optionalNotification.get();
        removeLikerAndUpdateNotification(notification, event);
    }

    private void removeLikerAndUpdateNotification(LikeNotification notification, LikeEvent event) {
        notification.removeLiker(event.getUserId(), Instant.now());

        if(notification.getLikerIds().isEmpty()) {
            removeService.deleteById(notification.getId());
        } else {
            saveService.upsert(notification);
        }
    }
}
