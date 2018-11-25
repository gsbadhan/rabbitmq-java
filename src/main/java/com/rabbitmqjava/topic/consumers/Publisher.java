package com.rabbitmqjava.topic.consumers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.56.101");
		connectionFactory.setUsername("mqtest");
		connectionFactory.setPassword("mqtest");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		//regular expersession based  routing key or queue
		String routingKey = "uni.#";
		String exchangeName = "tagTestExchange";
		channel.exchangeDeclare(exchangeName, "topic",false);
		for (int i = 1; i <= 20; i++) {
			String msg = "tagTestExchange msg #" + i;
			channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
			System.out.println("msg sent [" + msg + "] ");
		}
		channel.close();
		connection.close();
	}

}
