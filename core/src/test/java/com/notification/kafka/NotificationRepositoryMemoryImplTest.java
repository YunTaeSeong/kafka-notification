package com.notification.kafka;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryMemoryImplTest {

    private final NotificationRepositoryMemoryImpl sut = new NotificationRepositoryMemoryImpl();
    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @Test
    void test_save() throws Exception{
        // 저장 객체 생성과 저장
        sut.save(new Notification("1", 2L, NotificationType.LIKE, now, deletedAt));
        Optional<Notification> notification = sut.findById("1");

        // 조회했을 떄 객체가 있는가?
        assertTrue(notification.isPresent());
    }

    @Test
    void test_findById() throws Exception{
        sut.save(new Notification("2", 2L, NotificationType.LIKE, now, deletedAt));
        Optional<Notification> optionalNotification = sut.findById("2");

        Notification notification = optionalNotification.orElseThrow();
        assertEquals(notification.id, "2");
        assertEquals(notification.userId, 2L);
        assertEquals(notification.createdAt, now);
        assertEquals(notification.deletedAt, deletedAt);

    }

    @Test
    void test_delete_by_id() throws Exception{
        sut.save(new Notification("3", 2L, NotificationType.LIKE, now, deletedAt));
        sut.deleteById("3");

        Optional<Notification> optionalNotification = sut.findById("3");
        assertFalse(optionalNotification.isPresent());
    }


}