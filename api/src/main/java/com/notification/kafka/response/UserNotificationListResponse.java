package com.notification.kafka.response;

import com.notification.kafka.service.dto.GetUserNotificationResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "유저 알림 목록 응답")
public class UserNotificationListResponse {
    @Schema(defaultValue = "알림 목록")
    private List<UserNotificationResponse> notifications;

    @Schema(defaultValue = "다음 페이지 존재 여부")
    private boolean hasNext;

    @Schema(defaultValue = "다음 페이지 요청 시 전달할 pivot 파라미터")
    private Instant pivot;

    public static UserNotificationListResponse of(GetUserNotificationResult result) {
        // ConvertedNotification -> UserNotificationResponse
        List<UserNotificationResponse> notifications = result.getNotifications().stream()
                .map(UserNotificationResponse::of)
                .toList();

        // pivot 생성
        Instant pivot = (result.isHasNext() && !notifications.isEmpty())
                ? notifications.getLast().getOccurredAt() : null;

        return new UserNotificationListResponse(
                notifications,
                result.isHasNext(),
                pivot
        );
    }
}
