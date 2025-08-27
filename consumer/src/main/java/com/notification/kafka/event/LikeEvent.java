package com.notification.kafka.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class LikeEvent {
    private LikeEventType type;
    private Long postId;
    private Long userId;
    private Instant commentId; // 언제 좋아요를 눌렀는지
}
