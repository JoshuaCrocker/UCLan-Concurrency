package io.crocker.modelling.lesson06_maximumsubsequence;

import javax.swing.UIManager;

public class Max
{

    private boolean packFrame = false;

    //Construct the application
    public Max()
    {
        MaxFrame frame = new MaxFrame();
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame)
        {
            frame.pack();
        }
        else
        {
            frame.validate();
        }
        frame.setVisible(true);
    }

    //Main method
    public static void main(String[] args)
    {
        try
        {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
        }
        new io.crocker.modelling.lesson06_maximumsubsequence.Max();
    }
} 