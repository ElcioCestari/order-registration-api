package com.brotherselectronics.orderregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.cache.RedisCacheManager;

import static java.util.Objects.isNull;

@SpringBootApplication
@EnableCaching
public class OrderRegistrationApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderRegistrationApplication.class, args);
        checkCacheConnection(context);
    }

    private static void checkCacheConnection(ConfigurableApplicationContext context) {
        try {
            var cacheManager = (RedisCacheManager) context.getBean(CacheManager.class);
            var cache = cacheManager.getCache("redis_config_cache");
            if (isNull(cache)) {
                throw new RedisConnectionFailureException("Redis connection failure");
            }
            cache.putIfAbsent("redis_config_key", "redis_config_value");
            cache.evict("redis_config_key");
        } catch (RedisConnectionFailureException e) {
            e.printStackTrace();
            throw new RedisConnectionFailureException("Redis connection failure", e);
        }
    }

}
