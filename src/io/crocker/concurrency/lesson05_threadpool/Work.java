package io.crocker.concurrency.lesson05_threadpool;

public class Work implements Runnable
{
    private int jobNumber;

    public Work(int jobNumber)
    {
        this.jobNumber = jobNumber;
    }

    public void run()
    {
        System.out.println("Job " + jobNumber + " starting…");

        // Delay for a while to represent working time
        try
        {
            Thread.sleep(500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Job " + jobNumber + " finishing…");
    }

}
