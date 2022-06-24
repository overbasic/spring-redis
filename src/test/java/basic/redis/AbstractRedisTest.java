package basic.redis;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractRedisTest {

    protected static final GenericContainer redis;

    static {
        redis = new GenericContainer(
            DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);
        redis.start();
    }

}