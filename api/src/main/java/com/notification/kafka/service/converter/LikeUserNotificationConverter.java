package com.notification.kafka.service.converter;

import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.like.LikeNotification;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import com.notification.kafka.service.dto.ConvertedLikeNotification;
import com.notification.kafka.service.dto.ConvertedNotification;
import com.notification.kafka.user.User;
import com.notification.kafka.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public ConvertedLikeNotification convert(LikeNotification notification) {
        User user = userClient.getUser(notification.getLikerIds().getLast()); // 나의 정보가 아닌 작성자 Id
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedLikeNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                (long) notification.getLikerIds().size(),
                post.getImageUrl()
        );
    }
}
