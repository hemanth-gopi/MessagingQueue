package org.messagequeue.exceptions;

public class MessageTimeoutException extends Exception{

    public MessageTimeoutException(String message) {
        super(message);
    }
}
