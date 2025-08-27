package com.notification.kafka.event;

import lombok.Getter;

@Getter
public class CommentEvent {
    private CommentEventType type;
    private Long postId;
    private Long userId;
    private Long commentId;
}
