package com.notification.kafka.task;

import com.notification.kafka.event.LikeEvent;
import com.notification.kafka.like.LikeNotification;
import com.notification.kafka.notification.*;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import com.notification.kafka.service.NotificationGetService;
import com.notification.kafka.service.NotificationSaveService;
import com.notification.kafka.utils.NotificationIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeAddTask {

    private final PostClient postClient;
    private final NotificationGetService getService;
    private final NotificationSaveService saveService;

    public void processEvent(LikeEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if(post == null) {
            log.error("Post is null with postId: {}", event.getPostId());
            return;
        }

        // 본인이 본인 게시글에 좋아요를 누른 경우
        if(post.getUserId().equals(event.getUserId())) {
            return;
        }

        // 기존에 좋아요가 있었던 상황, 좋아요를 처음 누르는 상황
        saveService.upsert(createOrUpdateNotification(post, event));

    }

    private Notification createOrUpdateNotification(Post post, LikeEvent event) {
        Optional<Notification> optionalNotification = getService.getNotificationByTypeAndCommentId(NotificationType.LIKE, post.getId());

        Instant now = Instant.now();
        Instant retention = now.plus(90, ChronoUnit.DAYS);

        if(optionalNotification.isPresent()) {
            // 업데이트
            return updateNotification((LikeNotification) optionalNotification.get(), event, now, retention);
        } else {
            // 알림 신규 생성
            return createNotification(post, event, now, retention);
        }
    }

    private Notification updateNotification(LikeNotification notification, LikeEvent event, Instant now, Instant retention) {
        notification.addLiker(event.getUserId(), event.getCreatedAt(), now, retention);
        return notification;
    }

    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }

}
