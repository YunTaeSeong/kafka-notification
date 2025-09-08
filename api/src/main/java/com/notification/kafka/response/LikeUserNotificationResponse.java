package com.notification.kafka.response;

import com.notification.kafka.notification.NotificationType;
import com.notification.kafka.service.dto.ConvertedLikeNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.Instant;

@Getter
@Schema(description = "좋아요 알림 응답")
public class LikeUserNotificationResponse extends UserNotificationResponse {

    @Schema(description = "좋아요한 사용자 이름")
    private final String userName;

    @Schema(description = "좋아요한 사용자 프로필 이름")
    private final String userProfileImageUrl;

    @Schema(description = "좋아요한 사용자 총 개수")
    private final Long userCount;

    @Schema(description = "좋아요한 사용자 프로필 이미지")
    private final String postImageUrl;

    public LikeUserNotificationResponse(String id, NotificationType type, Instant occurredAt, String userName, String userProfileImageUrl, Long userCount, String postImageUrl) {
        super(id, type, occurredAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.userCount = userCount;
        this.postImageUrl = postImageUrl;
    }

    public static LikeUserNotificationResponse of(ConvertedLikeNotification notification) {
        return new LikeUserNotificationResponse(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getUserName(),
                notification.getPostImageUrl(),
                notification.getUserCount(),
                notification.getPostImageUrl()
        );
    }
}
