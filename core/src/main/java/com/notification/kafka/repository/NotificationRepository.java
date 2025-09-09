package com.notification.kafka.repository;

import com.notification.kafka.notification.Notification;
import com.notification.kafka.notification.NotificationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    Optional<Notification> findById(String id);

    Notification save(Notification notification);

    void deleteById(String id);

    // Mongo Json Query 문법
    // ?0, ?1 : 파라미터 순서
    @Query("{ 'type' : ?0, 'commentId' : ?1 }")
    Optional<Notification> findByTypeAndCommentId(NotificationType type, Long commentId);

    @Query("{'type' : ?0, 'postId' : ?1}")
    Optional<Notification> findByTypeAndPostId(NotificationType type, Long postId);

    @Query("{'type': ?0, 'userId': ?1, 'followerId': ?2}")
    Optional<Notification> findByTypeAndUserIdAndFollowerId(NotificationType type, Long userId, Long followerId);

    Slice<Notification> findAllByUserIdOrderByOccurredAtDesc(Long userId, Pageable pageable);

    Slice<Notification> findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(Long userId, Instant occurredAt, Pageable pageable);

    Optional<Notification> findFirstByUserIdOrderByLastUpdatedAtDesc(Long userId);
}
