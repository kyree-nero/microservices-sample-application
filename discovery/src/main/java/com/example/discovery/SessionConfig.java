package com.example.discovery;

import org.springframework.context.annotation.DependsOn;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@EnableRedisHttpSession
@DependsOn("redisServer")
public class SessionConfig
  extends AbstractHttpSessionApplicationInitializer {
}