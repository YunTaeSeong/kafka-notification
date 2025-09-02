package com.notification.kafka.comment;

import com.notification.kafka.Notification;
import com.notification.kafka.NotificationType;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;

// 댓글 알림을 담당 -> Mongo DB 저장
@Getter
@TypeAlias("CommentNotification") // 자바로 역직렬화를 할 때 자바로 어떤 클래스를 사용할것인지
public class CommentNotification extends Notification {

    private final Long postId;
    private final Long writerId;
    private final String comment;

    public CommentNotification(String id,
                               Long userId,
                               NotificationType type,
                               Instant occurredAt,
                               Instant createdAt,
                               Instant lastUpdatedAt,
                               Instant deletedAt, Long postId, Long writerId, String comment
    ) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.writerId = writerId;
        this.comment = comment;
    }
}
