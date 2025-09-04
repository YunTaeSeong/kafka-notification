package com.notification.kafka.service;

import com.notification.kafka.notification.Notification;
import com.notification.kafka.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSaveService {

    private final NotificationRepository repository;

    public void insert(Notification notification) {
        Notification result = repository.insert(notification);
        log.info("inserted: {}", result);
    }

    public void upsert(Notification notification) {
        Notification result = repository.save(notification);
        log.info("upserted: {}", result);
    }
}
