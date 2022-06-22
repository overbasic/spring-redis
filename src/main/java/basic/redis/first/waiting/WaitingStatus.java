package basic.redis.first.waiting;

public record WaitingStatus(
    boolean isAvailable,
    long waitingCount,
    long waitingTime
) {

}