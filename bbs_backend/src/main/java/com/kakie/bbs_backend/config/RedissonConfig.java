package com.kakie.bbs_backend.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Redisson配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConfig {
    private String port;
    private String host;
    @Bean
    public RedissonClient redissonClient() {
        // 1. Create config object
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", host, port);
        config.useSingleServer().setAddress(redisUrl).setDatabase(1);
        // 2. Create Redisson instance
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
