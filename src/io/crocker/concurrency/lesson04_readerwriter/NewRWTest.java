package io.crocker.concurrency.lesson04_readerwriter;

/**
 * <p>Title: Reader Writer</p>
 *
 * <p>Description: An example of a reader/writer lock</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Chris Casey
 * @version 1.0
 */

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NewRWTest
{
    io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Resource resource = new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Resource();

    static class Resource
    {
        java.util.Random rnd = new java.util.Random();

        public void read(String reader)
        {
            try
            {
                // read the data
                System.out.println("\t\t" + reader + " reading");
                try
                {
                    Thread.currentThread().sleep(rnd.nextInt(10));
                } catch (InterruptedException e)
                {
                }
                System.out.println("\t\t" + reader + " done");
            } finally
            {

            }
        }

        public void write(String writer)
        {
            try
            {
                // --write the necessary data--

                System.out.println("\t\t" + writer + " writing");
                try
                {
                    Thread.currentThread().sleep(rnd.nextInt(10));
                } catch (InterruptedException e)
                {
                }
                System.out.println("\t\t" + writer + " done");
            } finally
            {
            }
        }
    }

    class Writer extends Thread
    {
        private String name;
        private int numWrites;

        Writer(String name, int num)
        {
            this.name = name;
            numWrites = num;
        }

        public void run()
        {
            System.out.println("Starting " + name);
            for (int i = 0; i < numWrites; i++)
            {
                resource.write(name);
            }
            System.out.println("Stopping " + name);
        }
    }

    class Reader extends Thread
    {
        private String name;
        private int numReads;

        Reader(String name, int num)
        {
            this.name = name;
            numReads = num;
        }

        public void run()
        {
            System.out.println("Starting " + name);
            for (int i = 0; i < numReads; i++)
            {
                resource.read(name);
            }
            System.out.println("Stopping " + name);
        }
    }

    public NewRWTest()
    {
        System.out.println("ReadWriteLock using java.util.currency.lock");
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Writer("w/0", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Reader("r/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Writer("w/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Writer("w/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Reader("r/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.NewRWTest.Reader("r/3", 2).start();
    }


    public static void main(String[] args)
    {
        io.crocker.concurrency.lesson04_readerwriter.NewRWTest oldtest = new io.crocker.concurrency.lesson04_readerwriter.NewRWTest();
    }
}
