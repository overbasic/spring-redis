# Spring Redis Practice 

## Redis Connector
Spring에서 기본으로 설정되는 Connector인 `Lettuce`를 사용했습니다.
사용한 이유는 성능적인 면에서 `Jedis`보다 우수해서입니다.

## Redis Serializer
Redis Serializer를 key, value 모두 `StringRedisSerializer`를 사용했습니다.
이유는 기본적으로 사용되는 `JdkSerialzationRedisSerializer`를 사용하면 직렬화할 때 붙는 prefix 때문에 사용하지않았습니다.

## Test Configuration
기존에 사용하던 방식은 Docker를 직접 띄워서 Redis 테스트 환경을 구축했습니다.

Embedded 라이브러리를 사용할까도 했지만 최근까지 유지보수되는 라이브러리가 없었습니다.
운영체제마다 동일한 환경을 보장해주지 못하는 것도 문제였습니다.
- [https://github.com/kstyrc/embedded-redis]()
- [https://github.com/ozimov/embedded-redis]()

매번 Docker를 직접 띄워서하는 것도 번거로울 수 있기에
[TestContainers](https://www.testcontainers.org/)로 테스트 환경을 구축했습니다.

## Practice

### RedisRepository

### Pub Sub
[Spring message 예제](https://spring.io/guides/gs/messaging-redis/)를 참고하여 기능들을 추가했습니다.

#### Object
ObjectMapper를 이용해 변환했습니다.

#### Count
RedisTemplate을 상속받아 반환값이 없는 `convertAndSend`, 메시지를 받은 개수를 반환하게 했습니다.  