package com.notification.kafka.event;

import lombok.Data;

import java.time.Instant;

@Data
public class LikeEvent {
    private LikeEventType type;
    private Long postId;
    private Long userId;
    private Instant createdAt; // 언제 좋아요를 눌렀는지
}