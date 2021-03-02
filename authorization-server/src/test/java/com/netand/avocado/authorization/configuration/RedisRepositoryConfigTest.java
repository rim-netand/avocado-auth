package com.netand.avocado.authorization.configuration;

import com.netand.avocado.authorization.bootstrap.AuthorizationServerBootstrap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith( SpringExtension.class )
@SpringBootTest( classes = AuthorizationServerBootstrap.class )
class RedisRepositoryConfigTest {

	@Autowired
	StringRedisTemplate redisTemplate;

	@Test
	public void testString() {
		final String key = "auth-key";
		final ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();

		stringValueOperations.set( key, "3" );
		final String result_1 = stringValueOperations.get( key );

		System.out.println( "result_1 = " + result_1 );

		stringValueOperations.increment( key );
		final String result_2 = stringValueOperations.get( key );

		System.out.println( "result_2 = " + result_2 );
	}
}