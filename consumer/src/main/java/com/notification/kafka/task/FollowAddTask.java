package com.notification.kafka.task;

import com.notification.kafka.event.FollowEvent;
import com.notification.kafka.event.FollowEventType;
import com.notification.kafka.follow.FollowNotification;
import com.notification.kafka.notification.NotificationIdGenerator;
import com.notification.kafka.notification.NotificationSaveService;
import com.notification.kafka.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowAddTask {

    private final NotificationSaveService saveService;

    public void processEvent(FollowEvent event) {
        saveService.insert(createFollowNotification(event));
    }

    private static FollowNotification createFollowNotification(FollowEvent event) {
        Instant now = Instant.now();

        return new FollowNotification(
                NotificationIdGenerator.generate(),
                event.getTargetUserId(), // 이벤트를 받은 유저
                NotificationType.FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                event.getUserId() // 이벤트를 발생 시킨 유저
        );
    }
}
