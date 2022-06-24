package basic.redis.container;

import static org.assertj.core.api.Assertions.assertThat;

import basic.redis.AbstractRedisTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class RedisBackedCacheIntTest extends AbstractRedisTest {

    private static RedisBackedCache underTest;

    @BeforeAll
    static void setUp() {
        String address = redis.getHost();
        Integer port = redis.getFirstMappedPort();

        underTest = new RedisBackedCache(address, port);
    }

    @Test
    void testSimplePutAndGet() {
        underTest.put("test", "example");

        String retrieved = underTest.get("test");
        assertThat(retrieved).isEqualTo("example");
    }


    @Test
    void testSimpleGet() {
        String retrieved = underTest.get("test");
        assertThat(retrieved).isEqualTo(null);
    }
}