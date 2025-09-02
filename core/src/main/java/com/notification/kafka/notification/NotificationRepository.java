package com.notification.kafka.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

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
}
