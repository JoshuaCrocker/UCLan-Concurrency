package io.crocker.modelling.lesson08_recursion.listrecursion;

public class Node
{
    private int value;
    private Node next;

    public Node(int value, Node next)
    {
        this.value = value;
        this.next = next;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public io.crocker.modelling.lesson08_recursion.listrecursion.Node getNext()
    {
        return next;
    }

    public void setNext(io.crocker.modelling.lesson08_recursion.listrecursion.Node next)
    {
        this.next = next;
    }
}
