package io.crocker.concurrency.lesson04_readerwriter;

import java.util.Random;

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

public class Test
{
    io.crocker.concurrency.lesson04_readerwriter.Test.Resource resource = new io.crocker.concurrency.lesson04_readerwriter.Test.Resource();

    static class Resource
    {
        java.util.Random rnd = new java.util.Random();

        ReadWriteLock lock = new ReadWriteLock();

        public void read(String reader)
        {
            try
            {
                lock.requestRead();
                System.out.println("\t\t" + reader + " reading");
                try
                {
                    Thread.currentThread().sleep(rnd.nextInt(100));
                } catch (InterruptedException e)
                {
                }
                System.out.println("\t\t" + reader + " done");
            } finally
            {
                lock.readAccomplished();
            }
        }

        public void write(String writer)
        {
            try
            {
                lock.requestWrite();
                System.out.println("\t\t" + writer + " writing");
                try
                {
                    Thread.currentThread().sleep(rnd.nextInt(100));
                } catch (InterruptedException e)
                {
                }
                System.out.println("\t\t" + writer + " done");
            } finally
            {
                lock.writeAccomplished();
            }
        }

        public boolean read_if_possible()
        {
            if (lock.requestImmediateRead())
            {
                // in the real world, you'd actually do the read here
                lock.readAccomplished();
                return true;
            }
            return false;
        }

        public boolean write_if_possible()
        {
            if (lock.requestImmediateWrite())
            {
                // in the real world, you'd actually do the write here
                lock.writeAccomplished();
                return true;
            }
            return false;
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
                try
                {
                    Thread.sleep(1);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
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
                try
                {
                    Thread.sleep(1);
                } catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            System.out.println("Stopping " + name);
        }
    }

    public Test()
    {
        System.out.println("ReadWriteLock using Java primitives(synchronized)");
        if (!resource.read_if_possible())
        {
            System.out.println("Immediate read request didn't work");
        }
        if (!resource.write_if_possible())
        {
            System.out.println("Immediate write request didn't work");
        }
        new io.crocker.concurrency.lesson04_readerwriter.Test.Writer("w/0", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.Test.Reader("r/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.Test.Writer("w/1", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.Test.Writer("w/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.Test.Reader("r/2", 2).start();
        new io.crocker.concurrency.lesson04_readerwriter.Test.Reader("r/3", 2).start();
    }

    static public void main(String[] args)
    {
        io.crocker.concurrency.lesson04_readerwriter.Test t = new io.crocker.concurrency.lesson04_readerwriter.Test();
    }

}

