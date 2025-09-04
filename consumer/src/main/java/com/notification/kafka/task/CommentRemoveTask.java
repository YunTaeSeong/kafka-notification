package com.notification.kafka.task;

import com.notification.kafka.event.CommentEvent;
import com.notification.kafka.service.NotificationGetService;
import com.notification.kafka.service.NotificationRemoveService;
import com.notification.kafka.notification.NotificationType;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentRemoveTask {

    private final PostClient postClient;
    private final NotificationGetService getService;
    private final NotificationRemoveService removeService;

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if(Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        getService.getNotificationByTypeAndCommentId(NotificationType.COMMENT, event.getCommentId())
                .ifPresent(
                        notification -> removeService.deleteById(notification.getId())
                );
    }

}
