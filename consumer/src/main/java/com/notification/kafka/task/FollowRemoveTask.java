package com.notification.kafka.task;

import com.notification.kafka.event.FollowEvent;
import com.notification.kafka.notification.NotificationGetService;
import com.notification.kafka.notification.NotificationRemoveService;
import com.notification.kafka.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowRemoveTask {

    private final NotificationGetService getService;
    private final NotificationRemoveService removeService;

    public void processEvent(FollowEvent event) {
        getService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(), event.getUserId())
                .ifPresent(notification -> {
                    removeService.deleteById(notification.getId());
                });
    }
}
