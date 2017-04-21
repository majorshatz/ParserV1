/**
 * CS4380 W01
 Concepts of Programming Languages
 Professor: Jose M Garrido
 Students: Juan E. Tenorio Arzola, Thomas Nguyen, Andrew Shatz
 */

public class Node
{
    //Node current;
    token nToken;
    int home;// keeps track of branch in array.
    //place for node;
    Node next;
    public Node (){}

    public Node(token x, int counter)
    {
        home = counter;// keeps track of branch in array.
        nToken=x;

    }
    public void setChild(Node y)
    {
        next= y;
    }
    public Node getChild()
    {
        return next;
    }

}
