package com.notification.kafka.task;

import com.notification.kafka.comment.Comment;
import com.notification.kafka.comment.CommentClient;
import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.event.CommentEvent;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import com.notification.kafka.notification.Notification;
import com.notification.kafka.utils.NotificationIdGenerator;
import com.notification.kafka.service.NotificationSaveService;
import com.notification.kafka.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentAddTask {

    private final PostClient postClient;
    private final CommentClient commentClient;
    private final NotificationSaveService saveService;

    // 이벤트를 받아서 처리
    public void processEvent(CommentEvent event) {
        // 내가 작성한 댓글 알림 생성 X
        Post post = postClient.getPost(event.getPostId());
        if(Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        Comment comment = commentClient.getComment(event.getCommentId());

        // 알림 생성
        Notification notification = createNotification(post, comment);
        saveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();
        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(),
                NotificationType.COMMENT,
                comment.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getId()
        );
    }
}
