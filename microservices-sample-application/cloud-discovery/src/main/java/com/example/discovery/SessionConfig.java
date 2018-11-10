package com.example.discovery;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableRedisHttpSession
@Conditional(NotTest.class)
public class SessionConfig
  extends AbstractHttpSessionApplicationInitializer {
}