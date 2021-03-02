package com.netand.avocado.authorization.service.mq.rpc;

import com.netand.avocado.authorization.model.credentials.ClientDescription;
import com.netand.avocado.authorization.service.mq.rpc.model.MyMessage;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RPCDemoClient implements CommandLineRunner {
	public static final String directExchangeName = "rpc-exchange";
	public static final String routingKey = "rpc";

	@Autowired
	RpcClient rpcClient;

//	@Bean
//	DirectExchange exchange() {
//		return new DirectExchange(directExchangeName);
//	}

//	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(RPCDemoClient.class, args).close();
//	}

	@Override
	public void run(String... args) throws Exception {
		rpcClient.send( new MyMessage( "rim", "123456" ) );
	}

}

@Component
class RpcClient {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange exchange;

	public void send( MyMessage message) {
		System.out.println("Sending name: " + message.getId());
		template.setReplyTimeout(60000);

		String response = (String) template.convertSendAndReceive(exchange.getName(), RPCDemoClient.routingKey, message );
		System.out.println("Got '" + response + "'");
	}
}
