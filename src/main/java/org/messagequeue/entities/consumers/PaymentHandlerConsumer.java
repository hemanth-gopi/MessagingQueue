package org.messagequeue.entities.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.messagequeue.entities.Message;
import org.messagequeue.interfaces.Consumer;

import java.text.MessageFormat;

public class PaymentHandlerConsumer implements Consumer<String> {

    private final String NAME = "PAYMENT_HANDLER";

    private final ObjectMapper mapper = new ObjectMapper();

    public PaymentHandlerConsumer() {

    }

    private void consumeMessage(String message) {
        System.out.println(MessageFormat.format("Consumer : {0} | Message : {1}", NAME, message));

    }

    public void notify(String message) {
        consumeMessage(message);
    }
}
