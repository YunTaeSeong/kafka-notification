package com.notification.kafka.service.converter;

import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.post.Post;
import com.notification.kafka.post.PostClient;
import com.notification.kafka.service.dto.ConvertedCommentNotification;
import com.notification.kafka.service.dto.ConvertedLikeNotification;
import com.notification.kafka.service.dto.ConvertedNotification;
import com.notification.kafka.user.User;
import com.notification.kafka.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUserNotificationConverter {

    private final UserClient userClient;
    private final PostClient postClient;

    public ConvertedCommentNotification convert(CommentNotification notification) {
        User user = userClient.getUser(notification.getWriterId()); // 나의 정보가 아닌 작성자 Id
        Post post = postClient.getPost(notification.getPostId());

        return new ConvertedCommentNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdatedAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getComment(),
                post.getImageUrl()
        );
    }
}
