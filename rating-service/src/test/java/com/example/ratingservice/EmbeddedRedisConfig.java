package com.example.ratingservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisConfig {
	@Bean(destroyMethod="stop") RedisServer redisServer() throws Exception{
		RedisServer bean = new RedisServer(6379);
		bean.start();
		return bean;
	}
}
