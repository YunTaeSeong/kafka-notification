package com.notification.kafka.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class NotificationReadRepository {

    private final RedisTemplate<String, String> redisTemplate;

    // 알림을 읽은 시간
    public Instant setLastReadAt(Long userId) {
        Long lastReadAt = Instant.now().toEpochMilli();
        String key = getKey(userId);
        redisTemplate.opsForValue().set(key, String.valueOf(lastReadAt));
        redisTemplate.expire(key, 90, TimeUnit.DAYS);
        return Instant.ofEpochMilli(lastReadAt);
    }

    public Instant getLastReadAt(Long userId) {
        String key = getKey(userId);
        String lastReadAtStr = redisTemplate.opsForValue().get(key);
        if (lastReadAtStr == null) {
            return null;
        }

        long lastReadAtLong = Long.parseLong(lastReadAtStr);
        return Instant.ofEpochMilli(lastReadAtLong);
    }

    private String getKey(Long userId) {
        return userId + ":last  ReadAt";
    }
}
