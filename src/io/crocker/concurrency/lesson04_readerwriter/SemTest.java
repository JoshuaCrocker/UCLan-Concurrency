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
import java.util.concurrent.Semaphore;

public class SemTest
{
    io.crocker.concurrency.lesson04_readerwriter.SemTest.Resource resource = new io.crocker.concurrency.lesson04_readerwriter.SemTest.Resource();

    static class Resource
    {
        java.util.Random rnd = new java.util.Random();
        java.util.concurrent.Semaphore mutex = new java.util.concurrent.Semaphore(1);
        java.util.concurrent.Semaphore waitingReaderSemaphore = new java.util.concurrent.Semaphore(0);
        java.util.concurrent.Semaphore waitingWriterSemaphore = new java.util.concurrent.Semaphore(0);
        int activeWriters = 0;
        int activeReaders = 0;
        int waitingWriters = 0;
        int waitingReaders = 0;


        public void read(String reader)
        {
            try
            {
                mutex.acquire();

                if (activeWriters > 0)
                {
                    waitingReaders++;
                    mutex.release();
                    waitingReaderSemaphore.acquire();
                }
                else
                {
                    activeReaders++;
                    mutex.release();
                }

            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }

            // read the data
            System.out.println("\t\t" + reader + " reading");
            try
            {
                Thread.currentThread().sleep(rnd.nextInt(10) + 1);
            } catch (InterruptedException e)
            {
            }
            System.out.println("\t\t" + reader + " done");

            try
            {
                mutex.acquire();
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            activeReaders--;

            if (activeReaders == 0 && waitingWriters > 0)
            {
                waitingWriters--;
                activeWriters = 1;
                waitingWriterSemaphore.release();
            }
            mutex.release();
        }

        public void write(String writer)
        {
            try
            {
                mutex.acquire();

                if (activeReaders > 0 || activeWriters > 0)
                {
                    waitingWriters++;
                    mutex.release();
                    waitingWriterSemaphore.acquire();
                }
                else
                {
                    activeWriters++;
                    mutex.release();
                }

            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            // --write the necessary data--
            System.out.println("\t\t" + writer + " writing");
            try
            {
                Thread.currentThread().sleep(rnd.nextInt(10) + 1);
            } catch (InterruptedException e)
            {
            }
            System.out.println("\t\t" + writer + " done");

            try
            {
                mutex.acquire();
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
            if (rnd.nextDouble() > 0.5)
            {
                if (waitingWriters > 0)
                {
                    waitingWriters--;
                    activeWriters = 1;
                    waitingWriterSemaphore.release();
                }
                else if (waitingReaders > 0)
                {
                    activeReaders = waitingReaders;
                    waitingReaders = 0;
                    waitingReaderSemaphore.release(activeReaders);
                }
            }
            else
            {
                if (waitingReaders > 0)
                {
                    activeReaders = waitingReaders;
                    waitingReaders = 0;
                    waitingReaderSemaphore.release(activeReaders);
                }
                else if (waitingWriters > 0)
                {
                    waitingWriters--;
                    activeWriters = 1;
                    waitingWriterSemaphore.release();
                }
            }

            mutex.release();
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


    public SemTest()
    {
        System.out.println("Semaphore-based ReadWriteLock");
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Writer("w/0", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Reader("r/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Writer("w/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Writer("w/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Reader("r/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.SemTest.Reader("r/3", 2).start();
    }

    public static void main(String[] args)
    {
        io.crocker.concurrency.lesson04_readerwriter.SemTest oldtest = new io.crocker.concurrency.lesson04_readerwriter.SemTest();
    }
}
