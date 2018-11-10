package com.example.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import redis.embedded.RedisServer;

@SpringBootApplication
public class EmbeddedRedisApplication {
	public static void main(String[] args) {
        SpringApplication.run(EmbeddedRedisApplication.class, args);
    }
	
	@Bean(destroyMethod="stop") RedisServer redisServer() throws Exception{
		RedisServer bean = new RedisServer(6379);
		bean.start();
		return bean;
	}
}
