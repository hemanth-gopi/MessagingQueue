package org.messagequeue.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.MessageFormat;

public class Message {
    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("creation_time")
    private long creationTime;

    @JsonProperty("ttl")
    private long timeToLive;

    public Message(@JsonProperty("message") String message,
                   @JsonProperty("type") MessageType type,
                   @JsonProperty("creation_time") long creationTime,
                   @JsonProperty("ttl") long timeToLive) {
        this.message = message;
        this.type = type;
        this.creationTime = creationTime;
        this.timeToLive = timeToLive;
    }

    public String getMessage() {
        return this.message;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public MessageType getType() {
        return this.type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return MessageFormat.format(" Type : {0} | Message: {1}", type, message);
    }
}
