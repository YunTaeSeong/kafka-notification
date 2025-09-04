package com.notification.kafka.consumer;

import com.notification.kafka.event.FollowEvent;
import com.notification.kafka.event.FollowEventType;
import com.notification.kafka.task.FollowAddTask;
import com.notification.kafka.task.FollowRemoveTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class FollowEventConsumer {

    private final FollowAddTask followAddTask;
    private final FollowRemoveTask followRemoveTask;

    @Bean(name = "follow")
    public Consumer<FollowEvent> follow() {
        return event -> {
            if(event.getType() == FollowEventType.ADD) {
                followAddTask.processEvent(event);
            } else if(event.getType() == FollowEventType.REMOVE) {
                followRemoveTask.processEvent(event);
            }
        };
    }

}
