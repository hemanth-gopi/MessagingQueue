package org.messagequeue.interfaces;

public interface Consumer<T> {
    public void notify(T message);
}
