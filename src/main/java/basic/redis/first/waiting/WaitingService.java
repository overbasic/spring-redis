package basic.redis.first.waiting;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaitingService {

    private static final String PARTICIPATE_KEY = "participate";

    private static final long START_INDEX = 0L;

    private static final long PARTICIPATE_SIZE = 50L;

    private static final long END_INDEX = PARTICIPATE_SIZE - 1;

    private final RedisTemplate<String, Object> participationTemplate;

    private final ZSetOperations<String, Object> waitingQueue;

    private final ValueOperations<String, String> countOps;

    public void addQueue(String key, Long userId) {
        final long now = System.currentTimeMillis();
        waitingQueue.add(key, String.valueOf(userId), now);
        log.info("ID {} 님이 대기열에 추가되었습니다. {}", userId, new Date(now));
    }

    public void publish(String key) {
        waitingQueue.range(key, START_INDEX, END_INDEX)
            .forEach(participant -> {
                    participationTemplate.opsForSet().add(PARTICIPATE_KEY, participant);
                    waitingQueue.remove(key, participant);
                }
            );
    }

    public WaitingStatus getWaitingStatus(String key, Long userId) {
        Boolean isAvailable = participationTemplate.opsForSet()
            .isMember(PARTICIPATE_KEY, String.valueOf(userId));
        Long rank = waitingQueue.rank(key, String.valueOf(userId));
        if (rank == null) {
            rank = 0L;
        }
        long waitingTime = rank / PARTICIPATE_SIZE + 1;
        return new WaitingStatus(isAvailable, rank, waitingTime);
    }

    public boolean isEventEnd(String key) {
        String count = countOps.get(key);
        int remains = count != null ? Integer.parseInt(count) : 0;
        return remains <= 0;
    }
}