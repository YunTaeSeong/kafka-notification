package com.notification.kafka.service;

import com.notification.kafka.repository.NotificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LastReadAtService {

    private final NotificationReadRepository repository;

    public Instant setLastReadAt(Long userId) {
        return repository.setLastReadAt(userId);
    }

    public Instant getLastReadAt(Long userId) {
        return repository.getLastReadAt(userId);
    }

}
