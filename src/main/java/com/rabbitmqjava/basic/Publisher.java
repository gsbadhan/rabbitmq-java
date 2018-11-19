package com.rabbitmqjava.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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
		String queue = "directTestQueue";
		String exchangeName="";
		 channel.queueDeclare(queue, false, false, false, null);
		for (int i = 1; i <= 10; i++) {
			String msg = "directTestQueue msg #" + i;
			channel.basicPublish(exchangeName, queue, null, msg.getBytes());
			System.out.println("msg [" + msg + "] sent");
		}
		channel.close();
		connection.close();
	}

}
