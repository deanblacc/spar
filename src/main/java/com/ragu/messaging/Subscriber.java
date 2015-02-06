package com.ragu.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by nana on 11/18/14.
 */
public class Subscriber {
    private static final String EXCHANGE_NAME = "Test";

    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(System.getenv("RABBIT_HOST"));
        connectionFactory.setPort(Integer.parseInt(System.getenv("RABBIT_CONSUMER_PORT")));
        connectionFactory.setUsername(System.getenv("RABBIT_USERNAME"));
        connectionFactory.setPassword((System.getenv("RABBIT_PASSWORD")));
        connectionFactory.setVirtualHost(System.getenv("RABBIT_VHOST"));
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,false,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received " + message);
        }
    }
}
