package com.notification.kafka.service;

import com.notification.kafka.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationRemoveService {

    private final NotificationRepository repository;

    public void deleteById(String id) {
        log.info("deleted: {}", id);
        repository.deleteById(id);
    }

}
