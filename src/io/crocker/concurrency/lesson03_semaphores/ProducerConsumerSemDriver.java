package io.crocker.concurrency.lesson03_semaphores;

/**
 * <p>Title: Producer Consumer</p>
 *
 * <p>Description: Producer consumer program using semaphores</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @author Chris Casey
 * @version 1.0
 */
public class ProducerConsumerSemDriver
{
    public ProducerConsumerSemDriver()
    {
    }

    public static void main(String[] args)
    {
        Buffer b = new Buffer(2);

        Consumer c = new Consumer(b, 1);
        Consumer c2 = new Consumer(b, 2);
        Producer p = new Producer(1, b, 5);
        Producer p2 = new Producer(2, b, 5);

        c.start();
        c2.start();
        p.start();
        p2.start();

        try
        {
            p.join();
            c.join();
            c2.join();
            p2.join();
        } catch (InterruptedException ex)
        {
        }
    }
}
