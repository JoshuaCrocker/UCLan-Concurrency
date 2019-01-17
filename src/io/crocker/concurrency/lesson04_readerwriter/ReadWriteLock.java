package io.crocker.concurrency.lesson04_readerwriter;

import java.util.*;

/**
 * <p>Title: Reader Writer</p>
 *
 * <p>Description: An example of a reader/writer lock</p>
 *
 * @version 1.0
 */
public class ReadWriteLock
{
    private int activeReaders;     // = 0
    private int waitingReaders;    // = 0
    private int activeWriters;     // = 0

    private final java.util.LinkedList<Object> writerLocks = new java.util.LinkedList<Object>(); // not threadsafe


    public ReadWriteLock()
    {
        System.out.println("read write lock created");
    }

    public synchronized void requestRead()
    {
        if (activeWriters == 0 && writerLocks.size() == 0)
        {
            ++activeReaders;
        }
        else
        {
            ++waitingReaders;
            try
            {
                wait();
            } catch (InterruptedException e)
            {
            }
        }
    }

    public synchronized boolean requestImmediateRead()
    {
        if (activeWriters == 0 && writerLocks.size() == 0)
        {
            ++activeReaders;
            return true;
        }
        return false;
    }

    public synchronized void readAccomplished()
    {
        if (--activeReaders == 0)
        {
            notifyWriters();
        }
    }

    public void requestWrite()
    {
        // This method can't be synchronized or there'd be a nested-monitor
        // lockout problem: We have to acquire the lock for "this" in
        // order to modify the fields, but that lock must be released
        // before we start waiting for a safe time to do the writing.
        // If request_write() were synchronized, we'd be holding
        // the monitor on the Reader_writer lock object while we were
        // waiting. Since the only way to be released from the wait is
        // for someone to call either read_accomplished()
        // or write_accomplished() (both of which are synchronized),
        // there would be no way for the wait to terminate.
        Object lock = new Object();
        synchronized (lock)
        {
            synchronized (this)
            {
                boolean okay_to_write = writerLocks.size() == 0
                        && activeReaders == 0
                        && activeWriters == 0;
                if (okay_to_write)
                {
                    ++activeWriters;
                    return; // the "return" jumps over the "wait" call
                }
                writerLocks.addLast(lock);
            }
            try
            {
                lock.wait();
            } catch (InterruptedException e)
            {
            }
        }
    }

    synchronized public boolean requestImmediateWrite()
    {
        if (writerLocks.size() == 0 && activeReaders == 0
                && activeWriters == 0)
        {
            ++activeWriters;
            return true;
        }
        return false;
    }

    public synchronized void writeAccomplished()
    {
        // The logic here is more complicated than it appears.
        // If readers have priority, you'll  notify them. As they
        // finish up, they'll call readAccomplished(), one at
        // a time. When they're all done, readAccomplished() will
        // notify the next writer. If no readers are waiting, then
        // just notify the writer directly.
        --activeWriters;
        if (waitingReaders > 0)   // priority to waiting readers
        {
            notifyReaders();
        }
        else
        {
            notifyWriters();
        }
    }

    private void notifyReaders()
    { // must be accessed from a synchronized method
        activeReaders += waitingReaders;
        waitingReaders = 0;
        notifyAll();
    }

    private void notifyWriters()       // must be accessed from a
    {                                   //  synchronized method
        if (writerLocks.size() > 0)
        {
            Object oldest = writerLocks.removeFirst();
            ++activeWriters;
            synchronized (oldest)
            {
                oldest.notify();
            }
        }
    }


}


