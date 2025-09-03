package com.notification.kafka.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Document("notifications") // 자바 객체를 MongoDB 컬렉션에 매핑
public abstract class Notification {
    @Field(targetType = FieldType.STRING)
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private Instant deletedAt; // 알림이 삭제 될 시간
}
