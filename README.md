# kafka-notification
# 대용량 트래픽을 고려한 카프카 알림서버 구현

# 멀티 모듈 구현
# api, consumer, core
# api : 클라이언트 요청을 받는 서버가 뜸
# consumer : 카프카를 컨슈밍하여 알림을 만들고 MongoDB 에 저장, 서버가 뜸
# core : 서버가 뜨지는 않지만 api, consumer 가 core를 의존해서 공통 코드를 사용
