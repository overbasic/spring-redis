package basic.redis.queue;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

public class RedisTemplateWrapper extends StringRedisTemplate {

    private final RedisSerializer<String> stringSerializer = RedisSerializer.string();

    public RedisTemplateWrapper(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public Long convertAndSendBack(String channel, Object message) {
        Assert.hasText(channel, "a non-empty channel is required");

        byte[] rawChannel = rawString(channel);
        byte[] rawMessage = rawValue(message);

        return execute(connection ->
            connection.publish(rawChannel, rawMessage), true);
    }

    private byte[] rawString(String key) {
        return stringSerializer.serialize(key);
    }

    private byte[] rawValue(Object value) {
        return stringSerializer.serialize((String) value);
    }
}