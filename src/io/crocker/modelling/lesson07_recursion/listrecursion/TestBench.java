package io.crocker.modelling.lesson07_recursion.listrecursion;

public class TestBench
{
    public static void main(String[] args)
    {
        Node root = new Node(
                1,
                new Node(
                        2,
                        new Node(
                                3,
                                new Node(
                                        5,
                                        null
                                )
                        )
                )
        );

        ListMethods.print(root);

        ListMethods.routineA(4, root);
        ListMethods.print(root);

        ListMethods.routineB(3, root);
        ListMethods.print(root);

        ListMethods.routineA(3, root);
        ListMethods.routineA(3, root);
        ListMethods.routineA(3, root);
        ListMethods.routineA(3, root);
        ListMethods.print(root);

        ListMethods.routineC(3, root);
        ListMethods.print(root);
    }
}
