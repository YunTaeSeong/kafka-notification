package com.notification.kafka.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class FollowEvent {
    private FollowEventType type;
    private Long userId;
    private Long targetUserId;
    private Instant createdAt;
}
