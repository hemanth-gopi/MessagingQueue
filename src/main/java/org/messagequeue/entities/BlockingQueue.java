package org.messagequeue.entities;

import org.messagequeue.interfaces.Consumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingQueue<T> {
    private final int capacity;
    private final Queue<T> queue;
    private final Object lock = new Object();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();

    }

    public T poll() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                System.out.println("Queue is Empty. Waiting....");
                lock.wait();
            }
            T element = queue.poll();
            lock.notify();
            return element;

        }
    }


    /**
     * Tries to push an element to the back of the queue
     */
    public void push(T element) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == capacity) {
                System.out.println("Capacity of the queue is full. Waiting....");
                lock.wait();
            }

            queue.add(element);
            lock.notify();
        }
    }

}
