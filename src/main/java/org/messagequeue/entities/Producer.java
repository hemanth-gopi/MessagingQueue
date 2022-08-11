package org.messagequeue.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Producer implements Runnable {

    private BlockingQueue<String > queue;
    private final ObjectMapper mapper = new ObjectMapper();

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            produceMessages();
        }
    }

    public void produceMessages() {
        Message messageObject = new Message(randomString(20),
                getRandomMessageType(),
                System.currentTimeMillis(),
                getRandomTtl()
        );
        String message = null;
        try {
            message = mapper.writeValueAsString(messageObject);
        } catch (JsonProcessingException e) {
            System.out.println("Error converting Message object to String" + e);
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Pushing messages...." + message);
            queue.push(message);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println("Error pushing message to queue" + e);
            throw new RuntimeException(e);
        }
    }

    private MessageType getRandomMessageType() {
        Random random = new Random();
        return MessageType.values()[random.nextInt( MessageType.values().length)];
    }

    private long getRandomTtl(){
        Random random = new Random();
        return random.nextInt(9) * 1000;
    }

    private String randomString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
