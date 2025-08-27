package com.notification.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBootApplication
class NotificationRepositoryMemoryImplTest {

    @Autowired
    private NotificationRepository sut;

    private final Long userId = 2L;
    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @Test
    void test_save() throws Exception{
        // 저장 객체 생성과 저장
        sut.save(new Notification("1", userId, NotificationType.LIKE, now, deletedAt));
        Optional<Notification> notification = sut.findById("1");

        // 조회했을 떄 객체가 있는가?
        assertTrue(notification.isPresent());
    }

    @Test
    void test_findById() throws Exception{
        String id = "2";

        sut.save(new Notification(id, userId, NotificationType.LIKE, now, deletedAt));
        Optional<Notification> optionalNotification = sut.findById(id);

        Notification notification = optionalNotification.orElseThrow();
        assertEquals(notification.id, id);
        assertEquals(notification.userId, userId);
        assertEquals(notification.createdAt.getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.deletedAt.getEpochSecond(), deletedAt.getEpochSecond());

    }

    @Test
    void test_delete_by_id() throws Exception{
        String id = "3";

        sut.save(new Notification(id, userId, NotificationType.LIKE, now, deletedAt));
        sut.deleteById(id);
        Optional<Notification> optionalNotification = sut.findById(id);

        assertFalse(optionalNotification.isPresent());
    }


}