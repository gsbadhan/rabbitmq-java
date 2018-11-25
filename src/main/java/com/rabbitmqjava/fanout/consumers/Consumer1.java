package com.rabbitmqjava.fanout.consumers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmqjava.util.Common;

public class Consumer1 {
	private static String name = Consumer1.class.getSimpleName();

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.56.101");
		connectionFactory.setUsername("mqtest");
		connectionFactory.setPassword("mqtest");

		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		String queue = "party-a";
		channel.queueDeclare(queue, true, false, false, null);
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(name + " [x] Received '" + message + "'");
				channel.basicAck(envelope.getDeliveryTag(), false);
				Common.delay(1000);
			}
		};
		channel.basicConsume(queue, false, consumer);
		Common.keepOpenChannel(channel, queue);
		channel.close();
		connection.close();
	}

}
