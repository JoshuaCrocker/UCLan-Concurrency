package io.crocker.modelling.lesson07_partition;

public class JoshuaPartition implements io.crocker.modelling.lesson07_partition.IPartition
{
    @Override
    public int partition(int[] theArray)
    {
        int boundary = 0; // the first element
        int pivot = theArray[boundary];

        for (int i = boundary + 1; i < theArray.length; i++)
        {
            if (theArray[i] <= pivot)
            {
                int temp = theArray[i];
                theArray[i] = theArray[++boundary];
                theArray[boundary] = temp;
            }
        }

        return boundary;
    }
}
