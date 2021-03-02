package com.netand.avocado.authorization.component.eventListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RedisExpiredMessageEventListener implements MessageListener {

	public static List<String> messageList = new ArrayList<String>();

	public void onMessage( Message message, byte[] pattern ) {

		messageList.add(message.toString());

		log.info( "==========> Key expired. ( body:{}, channel:{} )", new String(message.getBody()), new String( message.getChannel()) );

	}
}
