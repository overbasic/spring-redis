package basic.redis.first.waiting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;

    @GetMapping
    public Flux<WaitingStatus> getWaitingStatus(
        @RequestParam Long userId,
        @RequestParam String eventKey) {
        return Flux.just(waitingService.getWaitingStatus(eventKey, userId));
    }

    @PostMapping
    public Flux<Void> addWaitingQueue(@RequestBody WaitingRequest request) {
        waitingService.addQueue(request.eventKey(), request.userId());
        return Flux.just();
    }
}