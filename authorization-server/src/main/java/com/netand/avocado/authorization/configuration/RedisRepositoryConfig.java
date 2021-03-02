package com.netand.avocado.authorization.configuration;

import com.netand.avocado.authorization.component.eventListener.RedisExpiredMessageEventListener;
import com.netand.avocado.authorization.redis.entity.AccessTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class RedisRepositoryConfig {

	private final RedisProperties redisProperties;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {

		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName( redisProperties.getHost() );
		config.setPort( redisProperties.getPort() );
		config.setDatabase( redisProperties.getDatabase() );
		config.setPassword( redisProperties.getPassword() );

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().commandTimeout( Duration.ofMillis( redisProperties.getTimeout() ) ).build();

		return new LettuceConnectionFactory( config, clientConfig );
	}

	@Bean
	public RedisTemplate< String, Object > redisTemplate() {

		RedisTemplate< String, Object > redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory( redisConnectionFactory() );
		redisTemplate.setKeySerializer( new StringRedisSerializer() );

		return redisTemplate;
	}

	@Bean
	public RedisTemplate< String, AccessTokenEntity > accessTokenEntityRedisTemplate() {

		RedisTemplate< String, AccessTokenEntity > template = new RedisTemplate<>();
		template.setConnectionFactory( redisConnectionFactory() );
		template.setValueSerializer( new Jackson2JsonRedisSerializer<>( AccessTokenEntity.class ) );

		return template;
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener( new MessageListenerAdapter(new RedisExpiredMessageEventListener()), new PatternTopic( "__keyevent@0__:expired" ));
//		container.addMessageListener( new MessageListenerAdapter(new RedisMessageSet()), new PatternTopic( "__keyevent@0__:KEA" ));
		return container;
	}
}
