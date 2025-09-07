package com.notification.kafka.service.converter;

import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.follow.FollowNotification;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import com.notification.kafka.service.dto.ConvertedCommentNotification;
import com.notification.kafka.service.dto.ConvertedFollowNotification;
import com.notification.kafka.service.dto.ConvertedNotification;
import com.notification.kafka.user.User;
import com.notification.kafka.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowUserNotificationConverter {

    private final UserClient userClient;

    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUser(notification.getFollowerId());
        boolean isFollowing = userClient.getIsFollowing(notification.getUserId(), notification.getFollowerId());

        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                isFollowing
        );
    }
}
