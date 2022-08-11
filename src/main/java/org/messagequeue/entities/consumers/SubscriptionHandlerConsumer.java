package org.messagequeue.entities.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.messagequeue.entities.Message;
import org.messagequeue.interfaces.Consumer;

import java.text.MessageFormat;

public class SubscriptionHandlerConsumer implements Consumer<String> {

    private final String NAME = "SUBSCRIPTION_HANDLER";

    private final ObjectMapper mapper = new ObjectMapper();

    public SubscriptionHandlerConsumer() {
    }

    private void consumeMessage(String message) {
        try {
            Message messageObject = mapper.readValue(message, Message.class);
            System.out.println(MessageFormat.format("Consumer : {0} | Message : {1}", NAME, messageObject));
            Thread.sleep(300);

        }catch (JsonMappingException e) {
            System.out.println("Error reading messageObject" + e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            System.out.println("Error reading messageObject" + e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void notify(String message) {
        consumeMessage(message);
    }
}
