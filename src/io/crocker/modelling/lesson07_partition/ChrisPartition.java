package io.crocker.modelling.lesson07_partition;

public class ChrisPartition implements io.crocker.modelling.lesson07_partition.IPartition
{
    @Override
    public int partition(int[] theArray)
    {
        int boundary = theArray.length - 1;
        int lower = 0;
        int pivot = theArray[boundary];

        while (lower <= boundary)
        {
            if (theArray[lower] <= pivot)
            {
                lower++;
            }
            else
            {
                int temp = theArray[lower];
                theArray[lower] = theArray[boundary];
                theArray[boundary] = temp;

                boundary--;
            }
        }

        return boundary;
    }
}
