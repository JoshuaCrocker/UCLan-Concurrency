/*
 * Buffer.java
 *
 * Created on 21 January 2008, 22:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package io.crocker.concurrency.lesson02_semaphores;

/**
 * @author CHRIS
 */
public class Buffer
{
    private String[] body;
    private int nextIn = 0;
    private int nextOut = 0;
     private java.util.concurrent.Semaphore mutex = new java.util.concurrent.Semaphore(1);
    private java.util.concurrent.Semaphore numAvail = new java.util.concurrent.Semaphore(0);
    private java.util.concurrent.Semaphore numFree;

    public Buffer(int size)
    {
        body = new String[size];
    }

    public void insert(String item)
    {
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
    }

    public String extract()
    {
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

        return res;
    }
}