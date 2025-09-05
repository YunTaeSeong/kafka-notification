package com.notification.kafka;

import com.notification.IntegrationTest;
import com.notification.kafka.comment.CommentNotification;
import com.notification.kafka.notification.Notification;
import com.notification.kafka.repository.NotificationRepository;
import com.notification.kafka.notification.NotificationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryMemoryImplTest extends IntegrationTest {

    @Autowired
    private NotificationRepository sut;

    @BeforeEach
    void setUp() {
        for(int i=1; i<=5; i++) {
            Instant occurredAt = now.minus(i, ChronoUnit.DAYS);
            sut.save(new CommentNotification(
                    "id-" + i, userId, NotificationType.COMMENT, occurredAt, now, now, ninetyDaysAfter, postId, writerId, comment, commentId)
            );
        }
    }

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

    private final long userId = 2L;
    private final long postId = 3L;
    private final long writerId = 4L;
    private final long commentId = 5L;
    private final String comment = "comment";
    private final Instant now = Instant.now();
    private final Instant ninetyDaysAfter = Instant.now().plus(90, ChronoUnit.DAYS);

    @Test
    void testSave() {
        String id = "1";
        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        assertTrue(optionalNotification.isPresent());
    }

    @Test
    void testFindById() {
        String id = "2";

        sut.save(createCommentNotification(id));
        Optional<Notification> optionalNotification = sut.findById(id);

        CommentNotification notification = (CommentNotification) optionalNotification.orElseThrow();
        assertEquals(notification.getId(), id);
        assertEquals(notification.getUserId(), userId);
        assertEquals(notification.getOccurredAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getCreatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getLastUpdatedAt().getEpochSecond(), now.getEpochSecond());
        assertEquals(notification.getDeletedAt().getEpochSecond(), ninetyDaysAfter.getEpochSecond());
        assertEquals(notification.getPostId(), postId);
        assertEquals(notification.getWriterId(), writerId);
        assertEquals(notification.getComment(), comment);
        assertEquals(notification.getCommentId(), commentId);
    }

    @Test
    void test_findAllByUserIdOrderByOccurredAtDesc() {
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Notification> notification = sut.findAllByUserIdOrderByOccurredAtDesc(userId, pageable);

        assertEquals(3, notification.getContent().size());
        assertTrue(notification.hasNext());

        Notification first = notification.getContent().get(0);
        Notification second = notification.getContent().get(1);
        Notification third = notification.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt())); // 두번째 보다 첫번째가 값이 크다 -> 최신순이라서 True -> 제일 값이 커야 위로 옴
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt())); // 세번째 보다 두번째가 값이 크다 -> 최신순이라서 True -> 값이 커야 위로 옴
    }


    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_firstQuery() {
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Notification> result = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        assertEquals(3, result.getContent().size());
        assertTrue(result.hasNext());

        Notification first = result.getContent().get(0);
        Notification second = result.getContent().get(1);
        Notification third = result.getContent().get(2);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
        assertTrue(second.getOccurredAt().isAfter(third.getOccurredAt()));
    }

    @Test
    void test_findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc_secondQueryWithPivot() {
        Pageable pageable = PageRequest.of(0, 3);

        Slice<Notification> firstResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, now, pageable);

        // Notification first = firResult.getContent().get(0);
        // Notification second = firResult.getContent().get(1);
        Notification last = firstResult.getContent().get(2); // Pivot 값은 3개 중 가장 마지막 값

        Instant pivot = last.getOccurredAt();
        Slice<Notification> secondResult = sut.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, pivot, pageable);

        assertEquals(2, secondResult.getContent().size());
        assertFalse(secondResult.hasNext());

        Notification first = secondResult.getContent().get(0);
        Notification second = secondResult.getContent().get(1);

        assertTrue(first.getOccurredAt().isAfter(second.getOccurredAt()));
    }

    @Test
    void testDeleteById() {
        String id = "3";

        sut.save(createCommentNotification(id));
        sut.deleteById(id);
        Optional<Notification> optionalNotification = sut.findById(id);

        assertFalse(optionalNotification.isPresent());
    }

    private CommentNotification createCommentNotification(String id) {
        return new CommentNotification(id, userId, NotificationType.LIKE, now, now, now, ninetyDaysAfter, postId, writerId, comment, commentId);
    }

}