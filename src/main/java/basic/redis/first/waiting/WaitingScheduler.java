package basic.redis.first.waiting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@EnableScheduling
@RequiredArgsConstructor
public class WaitingScheduler {

    private static final String REMAIN_KEY = "count";

    private static final String WAITING_KEY = "waiting";

    private final WaitingService waitingService;

    @Scheduled(fixedDelay = 1000)
    private void waitingScheduler() {
        if (waitingService.isEventEnd(REMAIN_KEY)) {
            log.info("===== 선착순 이벤트가 종료되었습니다. =====");
            return;
        }

        waitingService.publish(WAITING_KEY);
    }
}