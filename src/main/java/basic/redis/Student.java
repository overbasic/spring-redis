package basic.redis;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Student")
public record Student(Long id, String name) {

}