package basic.redis.first.waiting;

public record WaitingRequest(
    Long userId,
    String eventKey
) {

}