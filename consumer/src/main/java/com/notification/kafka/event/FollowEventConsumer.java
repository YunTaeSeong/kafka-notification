package com.notification.kafka.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class FollowEventConsumer {

    @Bean(name = "follow")
    public Consumer<FollowEvent> follow() {
        return event -> log.info(event.toString());
    }

}
