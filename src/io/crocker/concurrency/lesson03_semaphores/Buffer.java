/*
 * Buffer.java
 *
 * Created on 21 January 2008, 22:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package io.crocker.concurrency.lesson03_semaphores;

import java.util.concurrent.Semaphore;

/**
 * @author CHRIS
 */
public class Buffer
{
    private String[] body;
    private int nextIn = 0;
    private int nextOut = 0;

    private Semaphore mutex = new Semaphore(1);
    private Semaphore numAvail = new Semaphore(0);
    private Semaphore numFree;

    public Buffer(int size)
    {
        body = new String[size];
        numFree = new Semaphore(size);
    }

    public void insert(String item)
    {
        try
        {
            numFree.acquire();
            mutex.acquire();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        body[nextIn] = item;
        try
        {
            Thread.sleep((int) (Math.random() * 10));
        } catch (InterruptedException ex)
        {
        }

        nextIn++;

        if (nextIn == body.length)
        {
            nextIn = 0;
        }

        mutex.release();
        numAvail.release();
    }

    public String extract()
    {
        try
        {
            numAvail.acquire();
            mutex.acquire();
        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        String res = "";
        res = body[nextOut];
        try
        {
            Thread.sleep((int) (Math.random() * 10));
        } catch (InterruptedException ex)
        {
        }

        if (res == null)
        {
            res = "invalid item";
        }
        nextOut++;
        if (nextOut == body.length)
        {
            nextOut = 0;
        }

        mutex.release();
        numFree.release();

        return res;
    }
}