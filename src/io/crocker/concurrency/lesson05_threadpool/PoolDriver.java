package io.crocker.concurrency.lesson05_threadpool;

public class PoolDriver
{
    public static void main(String[] args)
    {
        WorkQueue queue = new WorkQueue(4);

        for (int i = 0; i < 10; i++)
        {
            queue.execute(new Work(i));
        }
    }
}
