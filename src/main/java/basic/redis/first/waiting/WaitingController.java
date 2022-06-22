package basic.redis.first.waiting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;

    @GetMapping
    public ResponseEntity<WaitingStatus> getWaitingStatus(
        @RequestParam Long userId,
        @RequestParam String eventKey) {
        return ResponseEntity.ok(waitingService.getWaitingStatus(eventKey, userId));
    }

    @PostMapping
    public ResponseEntity<Void> addWaitingQueue(@RequestBody WaitingRequest request) {
        waitingService.addQueue(request.eventKey(), request.userId());
        return ResponseEntity.ok().build();
    }
}