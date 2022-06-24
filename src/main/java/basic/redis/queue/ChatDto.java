package basic.redis.queue;

import java.io.Serializable;

public record ChatDto(
    String name,
    String message
) implements Serializable {

}