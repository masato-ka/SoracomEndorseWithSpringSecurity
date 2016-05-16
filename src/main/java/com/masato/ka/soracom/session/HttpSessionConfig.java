package com.masato.ka.soracom.session;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@EnableRedisHttpSession
public class HttpSessionConfig {

	@Bean
	HttpSessionStrategy
		httpSessionStrategy(){
		return new HeaderHttpSessionStrategy();
	}
	
}
