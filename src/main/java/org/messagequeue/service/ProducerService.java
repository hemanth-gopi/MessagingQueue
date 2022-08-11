package org.messagequeue.service;

import org.messagequeue.entities.BlockingQueue;
import org.messagequeue.entities.Message;
import org.messagequeue.entities.Producer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProducerService {
    final static Executor executor = Executors.newSingleThreadExecutor();

    private final BlockingQueue<String> queue;

    public ProducerService(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void produce() {
        executor.execute(new Producer(queue));
    }
}
