package basic.redis.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatReceiver.class);

    private AtomicInteger counter = new AtomicInteger();

    private ObjectMapper mapper = new ObjectMapper();

    public void receiveMessage(String message) throws JsonProcessingException {
        ChatDto chatDto = mapper.readValue(message, ChatDto.class);
        LOGGER.info("Received < {} > : {}", chatDto, counter.incrementAndGet());
    }

    public int getCount() {
        return counter.get();
    }
}