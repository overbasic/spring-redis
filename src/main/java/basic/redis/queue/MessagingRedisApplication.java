package basic.redis.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class MessagingRedisApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingRedisApplication.class);

    @Bean
    ChannelTopic chatTopic() {
        return new ChannelTopic("chat");
    }

    @Bean
    ChannelTopic foodTopic() {
        return new ChannelTopic("food");
    }

    @Bean
    RedisMessageListenerContainer container(
        RedisConnectionFactory connectionFactory,
        Map<String, ChannelTopic> channelTopics,
        Map<String, MessageListenerAdapter> messageListenerAdapters
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(chatListenerAdapter(), chatTopic());
        container.addMessageListener(foodListenerAdapter(), foodTopic());
        return container;
    }

    @Bean
    MessageListenerAdapter chatListenerAdapter() {
        return new MessageListenerAdapter(chatReceiver(), "receiveMessage");
    }

    @Bean
    MessageListenerAdapter foodListenerAdapter() {
        return new MessageListenerAdapter(foodReceiver(), "receiveMessage");
    }

    @Bean
    ChatReceiver chatReceiver() {
        return new ChatReceiver();
    }

    @Bean
    FoodReceiver foodReceiver() {
        return new FoodReceiver();
    }

    @Bean
    RedisTemplateWrapper template(RedisConnectionFactory connectionFactory) {
        return new RedisTemplateWrapper(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException, JsonProcessingException {

        ApplicationContext ctx = SpringApplication.run(MessagingRedisApplication.class, args);

        RedisTemplateWrapper template = ctx.getBean(RedisTemplateWrapper.class);
        FoodReceiver foodReceiver = ctx.getBean(FoodReceiver.class);
        ObjectMapper mapper = ctx.getBean(ObjectMapper.class);
        String message = mapper.writeValueAsString(new ChatDto("John", "hello "));

        while (foodReceiver.getCount() == 0) {
            LOGGER.info("Sending message...");

            Long count = template.convertAndSendBack("chat", message);
            LOGGER.info("Received Count... {}", count);

            count = template.convertAndSendBack("food", "FOOD deliver!");
            LOGGER.info("Received Count... {}", count);

            Thread.sleep(500L);
        }

        System.exit(0);
    }
}