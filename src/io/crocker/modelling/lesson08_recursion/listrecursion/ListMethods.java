package io.crocker.modelling.lesson08_recursion.listrecursion;

public class ListMethods
{
    public static Node routineA(int val, Node list)
    {
        if (list == null)
        {
            return new Node(val, null);
        }
        else if (list.getValue() >= val)
        {
            return new Node(val, list);
        }
        else
        {
            list.setNext(ListMethods.routineA(val, list.getNext()));
            return list;
        }
    }

    public static Node routineB(int val, Node list)
    {
        if (list == null)
        {
            return null;
        }
        else if (list.getValue() == val)
        {
            return list.getNext();
        }
        else
        {
            list.setNext(ListMethods.routineB(val, list.getNext()));
            return list;
        }
    }

    public static Node routineC(int val, Node list)
    {
        if (list == null)
        {
            return null;
        }
        else if (list.getValue() == val)
        {
            return ListMethods.routineC(val, list.getNext());
        }
        else
        {
            list.setNext(ListMethods.routineC(val, list.getNext()));
            return list;
        }
    }

    public static void print(Node list)
    {
        System.out.print(list.getValue());
        System.out.print(" ");

        if (list.getNext() != null)
        {
            ListMethods.print(list.getNext());
        }
        else
        {
            System.out.println();
        }
    }
}
