package com.notification.kafka;

import org.bson.types.ObjectId;

public class NotificationIdGenerator {

    public static String generate() {
        // 몽고 db 에서 생성하는 Id
        return new ObjectId().toString();
    }
}
