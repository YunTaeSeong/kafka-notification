package com.notification.kafka.event;

import com.notification.kafka.task.CommentAddTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommentEventConsumer {

    private final CommentAddTask commentAddTask;

    // 여기서 정의된 함수의 이름이 application-event.yaml에 definition에 정의한 이름과 같아야함
    @Bean(name = "comment")
    public Consumer<CommentEvent> comment() {
        return event -> {
            if(event.getType() == CommentEventType.ADD) {
                commentAddTask.processEvent(event);
            }
        };
    }
}
