package com.notification.kafka.api;

import com.notification.kafka.event.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
public class EventConsumerTestController implements EventConsumerTestControllerSpec{

    private final Consumer<CommentEvent> comment;

    @PostMapping("/test/comment")
    @Override
    public void comment(@RequestBody CommentEvent event) {
        comment.accept(event);
    }
}
