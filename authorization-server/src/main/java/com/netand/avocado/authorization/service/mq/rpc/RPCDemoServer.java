package com.netand.avocado.authorization.service.mq.rpc;

import com.netand.avocado.authorization.model.credentials.ClientDescription;
import com.netand.avocado.authorization.service.mq.rpc.model.MyMessage;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Configuration
public class RPCDemoServer implements CommandLineRunner {
	public static final String directExchangeName = "rpc-exchange";
	public static final String queueName = "rpc-queue";
	public static final String routingKey = "rpc";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(directExchangeName);
	}

	@Bean
	Binding binding( Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

//	public static void main(String[] args) throws InterruptedException {
//		SpringApplication.run(RPCDemoServer.class, args).close();
//	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Waiting...");
		new Scanner(System.in).nextLine();

	}
}

@Component
class RpcServer {

	@RabbitListener(queues = RPCDemoServer.queueName)
	public String LongRunningTask( MyMessage message ) throws InterruptedException {
		System.out.println("Received: " + message.toString());
		//Long running processing
		TimeUnit.SECONDS.sleep(5);
		return "Hello world, " + message.getId();
	}

}
