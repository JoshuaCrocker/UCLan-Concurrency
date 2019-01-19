package io.crocker.modelling.lesson06_partition;

import java.io.*;
import java.util.*;

/**
 * <p> A program to partition a 1-d array of integers</p>
 */
public class PartitionDemo
{

    private static int[] stringToIntegerArray(String s)
    {
        int endpos;
        Vector v = new Vector();

        while (s.length() > 0)
        {
            endpos = s.indexOf(' ');

            String front;

            if (endpos == -1)
            {
                front = s;
                s = "";
            }
            else
            {
                front = s.substring(0, endpos).trim();
                s = s.substring(endpos).trim();
            }

            v.add(Integer.parseInt(front));
        }

        int len = v.size();
        int[] theArray = new int[len];
        for (int i = 0; i < len; i++)
        {
            theArray[i] = (Integer) v.get(i);
        }
        return theArray;
    }

    public static void main(String[] args)
    {
        BufferedReader formattedInput
                = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("enter numbers on a single line\n");

        try
        {
            // Read the numbers and convert to an integer array
            String s = formattedInput.readLine();
            int[] theNumbers = stringToIntegerArray(s);

            // Display the array
            for (int i = 0; i < theNumbers.length; i++)
            {
                System.out.print(theNumbers[i] + " ");
            }
            System.out.println();

            if (theNumbers.length < 1)
            {
                System.out.println("No numbers entered!!");
                return;
            }

            // Partition the array
            IPartition partitionHandler = new JoshuaPartition();
            int boundary = partitionHandler.partition(theNumbers);

            // Display the results, indicated the boundary
            for (int i = 0; i < theNumbers.length; i++)
            {
                System.out.print(theNumbers[i] + " ");
                if (i == boundary)
                {
                    System.out.print("<= || > ");
                }
            }
            System.out.println();
            System.out.println("Press Return to continue");
            formattedInput.readLine();
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
            try
            {
                formattedInput.readLine();
            } catch (Exception e)
            {
            }
        }
    }
}
