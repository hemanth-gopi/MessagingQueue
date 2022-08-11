package org.messagequeue;

import org.messagequeue.entities.BlockingQueue;
import org.messagequeue.entities.Message;
import org.messagequeue.entities.MessageType;
import org.messagequeue.entities.consumers.PaymentHandlerConsumer;
import org.messagequeue.entities.consumers.SubscriptionHandlerConsumer;
import org.messagequeue.service.ConsumerService;
import org.messagequeue.service.ProducerService;

public class Main {


    public static void main(String[] args) {
        System.out.println("Message queue Testing class");

        BlockingQueue<String> queue = new BlockingQueue<>(20);

        ProducerService producerService = new ProducerService(queue);
        producerService.produce();

        ConsumerService consumerService = new ConsumerService<>(10, queue);
        consumerService.addConsumer(MessageType.PAYMENT, new PaymentHandlerConsumer());
        consumerService.addConsumer(MessageType.SUBSCRIPTION, new SubscriptionHandlerConsumer());
        consumerService.start();


    }
}