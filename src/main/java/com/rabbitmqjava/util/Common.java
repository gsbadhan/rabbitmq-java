package com.rabbitmqjava.util;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class Common {
	private Common() {
	}

	public static void delay(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// its optional code to keep-alive channel
	public static void keepOpenChannel(Channel channel, String queue) throws IOException {
		do {
			Common.delay(60 * 1000);
		} while (channel.messageCount(queue) <= 0 || channel.messageCount(queue) >= 0);
	}
}
