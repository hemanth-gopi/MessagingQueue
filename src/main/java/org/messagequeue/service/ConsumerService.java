package org.messagequeue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.messagequeue.constants.GlobalConstants;
import org.messagequeue.entities.BlockingQueue;
import org.messagequeue.entities.Message;
import org.messagequeue.entities.MessageType;
import org.messagequeue.exceptions.MessageTimeoutException;
import org.messagequeue.interfaces.Consumer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.spi.LocaleServiceProvider;

public class ConsumerService<T> {

    private final Map<MessageType, List<Consumer<String>>> consumers;
    private final BlockingQueue<String> queue ;
    private final int capacity;

    private final ExecutorService executor = Executors.newFixedThreadPool(20);

    public ConsumerService(int capacity, BlockingQueue<String> queue) {
        this.capacity = capacity;
        this.consumers = new HashMap<>();
        this.queue = queue;
    }

    public boolean addConsumer(MessageType type, Consumer<String> consumer) {
        if (consumers.size() == capacity) {
            return false;
        }
        consumers.putIfAbsent(type, new ArrayList<>());
        return consumers.get(type).add(consumer);
    }

   public void consumeMessages() {
        try {
            String message = queue.poll();
            ObjectMapper mapper = new ObjectMapper();
            Message messageObject = mapper.readValue(message, Message.class);

            assertExpiry(messageObject);

            for(Consumer<String> consumer: consumers.getOrDefault(messageObject.getType(), new ArrayList<>())) {
                consumer.notify(message);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (MessageTimeoutException e) {
            System.out.println(GlobalConstants.ANSI_RED + e.getMessage() + GlobalConstants.ANSI_RESET);
            //throw new RuntimeException(e);
        }
   }

    private void assertExpiry(Message messageObject) throws MessageTimeoutException{
        if(System.currentTimeMillis() > messageObject.getCreationTime() + messageObject.getTimeToLive()) {
            throw new MessageTimeoutException("Message expired! => " + messageObject);
        }
    }

    public void start() {
        while(true) {
            executor.execute(this::consumeMessages);
        }
   }


}
