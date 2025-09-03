package com.notification.kafka.event;

import com.notification.kafka.task.LikeAddTask;
import com.notification.kafka.task.LikeRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeEventConsumer {

    private final LikeAddTask likeAddTask;
    private final LikeRemoveTask likeRemoveTask;

    @Bean(name = "like")
    public Consumer<LikeEvent> like() {
        return event -> {
            if(event.getType() == LikeEventType.ADD) {
                likeAddTask.processEvent(event);
            } else if(event.getType() == LikeEventType.REMOVE) {
                likeRemoveTask.processEvent(event);
            }
        };
    }
}
