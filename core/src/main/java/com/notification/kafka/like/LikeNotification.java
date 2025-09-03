package com.notification.kafka.like;

import com.notification.kafka.notification.Notification;
import com.notification.kafka.notification.NotificationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
public class LikeNotification extends Notification {
    private final Long postId;
    private final List<Long> likerIds;

    public LikeNotification(String id, Long userId, NotificationType type, Instant occurredAt, Instant createdAt, Instant lastUpdatedAt, Instant deletedAt, Long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdatedAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(Long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.getLikerIds().add(likerId);
        this.setOccurredAt(occurredAt); // 이벤트가 발생한 시점에서는 api로 occurredAt 으로 정렬을 해서 List 에서 최상단 나오게 정렬
        this.setLastUpdatedAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLiker(Long likerId, Instant now) {
        this.getLikerIds().remove(likerId);
        this.setLastUpdatedAt(now);
    }
}
