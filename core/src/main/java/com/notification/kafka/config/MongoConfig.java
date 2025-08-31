package com.notification.kafka.config;

import com.mongodb.ConnectionString;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Slf4j
public class MongoConfig {

    private static final String MONGODB_IMAGE_NAME = "mongo:5.0";
    private static final int MONGODB_INNER_PORT = 27017;
    private static final String DATABASE_NAME = "notification";
    private static final GenericContainer<?> mongo = createMongoInstance();

    private static GenericContainer<?> createMongoInstance() {
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
                .withExposedPorts(MONGODB_INNER_PORT) // 컨테이너 내부에서 열려있는 포트를 외부에서 접근하도록 매핑
                .withReuse(true); // Testcontainers 는 테스트 실행이 끝나면 컨테이너를 종료하고 삭제하지만 -> 종료하지 않고 재사용 가능
    }

    @PostConstruct
    public void startMongo() {
        try {
            mongo.start();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @PreDestroy
    public void stopMongo() {
        try {
            mongo.stop();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    private ConnectionString connectionString() {
        String host = mongo.getHost();
        Integer port = mongo.getMappedPort(MONGODB_INNER_PORT);
        return new ConnectionString("mongodb://" + host + ":" + port + "/" + DATABASE_NAME);
    }

}
