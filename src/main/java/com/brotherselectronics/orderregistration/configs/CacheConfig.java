package com.brotherselectronics.orderregistration.configs;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Kept out of the main application class so test slices such as {@code @DataMongoTest},
 * which have no CacheManager, do not pick it up.
 */
@Configuration
@EnableCaching
public class CacheConfig {
}
