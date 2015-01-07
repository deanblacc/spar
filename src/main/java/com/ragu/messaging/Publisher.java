package com.ragu.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Publisher {

    private Channel channel;
    private Connection connection;
    private String exchangeName;

    public Publisher(String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "fanout");
    }

    public void closeAll() throws IOException {
        channel.close();
        connection.close();
    }

    public void publishMessage(String message) throws IOException {
        channel.basicPublish(exchangeName,"",null,message.getBytes());
    }
}
