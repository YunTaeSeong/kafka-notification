## kafka-notification
- 인스타그램 알림센터를 참고하여 알림센터를 위한 마이크로서비스를 구현
- 알림 종류: 게시물 댓글 알림, 게시물 좋아요 알림, 팔로우 알림 

## 멀티 모듈 구현
  - api, consumer, core
  - api : 클라이언트 요청을 받는 서버가 뜸
  - consumer : 카프카를 컨슈밍하여 알림을 만들고 MongoDB 에 저장, 서버가 뜸
  - core : 서버가 뜨지는 않지만 api, consumer 가 core를 의존해서 공통 코드를 사용

## 사용 기술
- Java, Spring Boot, Kafka, Redis, Docker Compose, Junit5, Test Container

## 알림센터 서비스 실행 방법
1. Docker Desktop 실행
   - Docker Daemon 이 실행되면서 Container 를 실행
2. Docker Compose 로 Kafka, MongoDB, Redis 실행
   - 명령어: `docker compose -f docker-compose.yaml up -d`
   - 실행파일: [docker-compose-single-kafka.yaml]
   - 카프카 명령어 정리: [docker-command.md]
3. Consumer 서비스 실행
   - `8081 포트`로 실행
   - Swagger 주소: http://localhost:8081/swagger-ui/index.html
4. API 서비스 실행
   - `8080 포트`로 실행
   - Swagger 주소: http://localhost:8080/swagger-ui/index.html

## 테스트 실행 방법
- `test profile` 이 활성화 됨
- Test Container 를 통해 실행된 MongoDB 와 Redis 인스턴스를 사용하여 독립적인 테스트 환경을 구축

## MongoDB Index
| 인덱스 생성 쿼리                                                                                               | 설명                                                      |
|------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------|
| db.notifications.createIndex({ "deletedAt":1 }, { "name": "ix_deletedAt","expireAfterSeconds": 0 })        | 90일 지난 알림 삭제 시 사용하는 TTL 인덱스                         |
| db.notifications.createIndex({ userId: 1, occurredAt: -1 }, { name: "ix_userId_occurredAt" })              | 유저별 알림 목록 조회 시 사용하는 인덱스                            |
| db.notifications.createIndex({ userId: 1, lastUpdatedAt: -1 }, { name: "ix_userId_lastUpdatedAt" })        | 신규 알림 여부 조회 시 `latestUpdatedAt`을 구하기 위해 사용하는 인덱스 |
| db.notifications.createIndex({ type: 1, postId: 1 }, { name: "ix_type_postId" })                           | 좋아요 알림 생성/삭제 시 사용하는 인덱스                            |
| db.notifications.createIndex({ type: 1, commentId: 1 }, { name: "ix_type_commentId" })                     | 댓글 알림 삭제 시 사용하는 인덱스                                  |
| db.notifications.createIndex({ type: 1, userId: 1, followerId: 1 }, { name: "ix_type_userId_followerId" }) | 팔로우 알림 삭제 시 사용하는 인덱스                               |
