package com.stephen.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.stephen.myblog.mapper")
@EnableConfigurationProperties
public class MyblogApplication {


	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}


	@Configuration
	@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
	public class RedisSessionConfig {
		@Bean
		public JedisConnectionFactory connectionFactory() {
			return new JedisConnectionFactory();
		}
	}
}
