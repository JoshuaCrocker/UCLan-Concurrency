package io.crocker.modelling.lesson06_maximumsubsequence;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;

public class MaxFrame extends javax.swing.JFrame
{

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private JButton jButton1 = new JButton();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea output = new JTextArea();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTextArea input = new JTextArea();
    private JPanel jPanel2 = new JPanel();
    private JPanel jPanel3 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();

    //Construct the frame
    public MaxFrame()
    {
        enableEvents(java.awt.AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            jbInit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Component initialization
    private void jbInit() throws Exception
    {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize(new java.awt.Dimension(400, 300));
        this.setTitle("Maximum Subsequence");
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setRows(2);
        jButton1.setText("Find maximum");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        input.setText("1 -3 4 -2 5 -3");
        jPanel2.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        jLabel1.setText("Output");
        jLabel2.setText("Input");
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, null);
        jPanel2.add(jScrollPane1, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.NORTH);
        jScrollPane1.getViewport().add(output, null);
        jPanel1.add(jPanel3, null);
        jPanel3.add(jScrollPane2, BorderLayout.CENTER);
        jPanel3.add(jLabel2, BorderLayout.NORTH);
        jScrollPane2.getViewport().add(input, null);
        this.getContentPane().add(jButton1, BorderLayout.SOUTH);
    }

    //Overridden so we can exit on System Close
    protected void processWindowEvent(java.awt.event.WindowEvent e)
    {
        super.processWindowEvent(e);
        if (e.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING)
        {
            System.exit(0);
        }
    }

    void jButton1_actionPerformed(java.awt.event.ActionEvent e)
    {

        output.setText("");

        java.util.Vector v = new java.util.Vector();
        String s = input.getText().trim();
        int endpos;

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

            int val = Integer.parseInt(front);
            v.add(new Integer(val));
        }

        int len = v.size();

        int[] theArray = new int[len];

        for (int i = 0; i < len; i++)
        {
            theArray[i] = ((Integer) v.get(i)).intValue();
        }

        int max = 0;
        int maxStart = -1;
        int maxEnd = -1;

        int start;
        int end;
        int total;

        for (start = 0; start < theArray.length; start++)
        {
            for (end = start; end < theArray.length; end++)
            {
                total = 0;
                for (int pos = start; pos <= end; pos++)
                {
                    total = total + theArray[pos];
                    if (total > max)
                    {
                        max = total;
                        maxStart = start;
                        maxEnd = end;
                    }
                }
            }
        }

        output.append("Using a cubic method: The maximum substring of \n");
        for (int i = 0; i < len; i++)
        {
            output.append(Integer.toString(theArray[i]) + " ");
        }

        output.append("\nis " + max + " between " + maxStart + " and " + maxEnd + "\n");

        for (start = 0; start < theArray.length; start++)
        {
            total = 0;
            for (end = start; end < theArray.length; end++)
            {
                total = total + theArray[end];
                if (total > max)
                {
                    max = total;
                    maxStart = start;
                    maxEnd = end;
                }
            }
        }

        output.append("Using a quadratic method: The maximum substring of \n");
        for (int i = 0; i < len; i++)
        {
            output.append(Integer.toString(theArray[i]) + " ");
        }

        output.append("\nis " + max + " between " + maxStart + " and " + maxEnd + "\n");

        total = 0;
        for (end = start; end < theArray.length; end++)
        {
            total = total + theArray[end];
            if (total > max)
            {
                max = total;
                maxStart = start;
                maxEnd = end;
            }
            else if (total < 0)
            {
                total = 0;
            }
        }

        output.append("Using a linear method: The maximum substring of \n");
        for (int i = 0; i < len; i++)
        {
            output.append(Integer.toString(theArray[i]) + " ");
        }

        output.append("\nis " + max + " between " + maxStart + " and " + maxEnd + "\n");
    }
}
