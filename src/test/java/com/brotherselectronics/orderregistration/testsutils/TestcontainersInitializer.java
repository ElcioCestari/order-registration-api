package com.brotherselectronics.orderregistration.testsutils;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

/**
 * Points every Spring Boot test context at throwaway MongoDB and Redis containers,
 * so tests never touch a developer's local database.
 *
 * <p>Registered in {@code src/test/resources/META-INF/spring.factories}, so it applies to
 * {@code @SpringBootTest} and {@code @DataMongoTest} without changing each test class.
 * The containers are started once per JVM and shared by all contexts; Testcontainers
 * removes them when the JVM exits.
 */
public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    // Same versions as docker-compose.yml
    private static final MongoDBContainer MONGO = new MongoDBContainer(DockerImageName.parse("mongo:6.0"));
    private static final GenericContainer<?> REDIS =
            new GenericContainer<>(DockerImageName.parse("redis:7-alpine")).withExposedPorts(6379);

    static {
        Startables.deepStart(MONGO, REDIS).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestPropertyValues.of(
                "spring.data.mongodb.uri=" + MONGO.getReplicaSetUrl("order-registration-test"),
                "spring.redis.host=" + REDIS.getHost(),
                "spring.redis.port=" + REDIS.getMappedPort(6379)
        ).applyTo(context.getEnvironment());
    }
}
