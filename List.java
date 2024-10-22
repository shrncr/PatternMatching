/* ***************************************************
 * Sara Hrnciar
 * 10/03/2024
 *
 * List Class - handles any form of data
 *************************************************** */

public class List<Type>
{
    // We don't actually have to set a max size with linked lists
    // But it is a good idea.
    // Just picture an infinite loop adding to the list! :O
    public final int MAX_SIZE = 50000;

    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    // constructor
    // remember that an empty list has a "size" of -1 and its "position" is at -1
    public List()
    {
        this.num_items = 0;
        this.curr = null;
    }

    // copy constructor
    // clones the list l and sets the last element as the current
    public List(List<Type> l)
    {
        Node<Type> n = l.head;

        this.head = this.tail = this.curr = null;
        this.num_items = 0;

        while (n != null && !this.IsFull())
        {
            this.InsertAfter(n.getData());
            n = n.getLink();
        }
    }
    public Node<Type> getHead(){
        return this.head;
    }
    public Node<Type> getTail(){
        return this.tail;
    }

    // navigates to the beginning of the list
    public void First()
    {
        this.curr = this.head;
    }

    // navigates to the end of the list
    // the end of the list is at the last valid item in the list
    public void Last()
    {
        this.curr = this.tail;
    }

    // navigates to the specified element (0-index)
    // this should not be possible for an empty list
    // this should not be possible for invalid positions
    // navigates to the specified element (0-indexed)
// this should not be possible for an empty list
// this should not be possible for invalid positions
public void SetPos(int pos) {
    if (this.IsEmpty() || pos < 0 || pos >= this.GetSize()) {
        return; // Invalid position or empty list, do nothing
    }
    
    this.curr = this.head;
    
    for (int i = 0; i < pos; i++) {
        this.Next();
    }
}


    // navigates to the previous element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Prev()
    {
        int pos = this.GetPos();
        if  (pos != 0 || this.num_items !=-1){
            this.First();
            for (int i = 0; i<pos-1; i++){
                this.Next();
            }
        }
    }

    
    // navigates to the next element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Next()
    {
        if  (!this.getTail().equals(this.curr)  && this.num_items !=-1){
            this.curr = this.curr.getLink();
        }

    }

    // returns the location of the current element (or -1)
    public int GetPos()
    {
        if (this.IsEmpty()){
            return -1;
        }else if (this.head == this.curr){
            return 0;
        }else{
            Node temp = this.head;
            int pos=0;
            while (!temp.equals(this.curr)){
                pos++;
                temp=temp.getLink();
            }
            return pos;
        }
    }

    // returns the value of the current element (or -1)
    public Type GetValue()
    {
        if (this.curr == null){
            return null;
        }
        return this.curr.getData();
    
    }

    // returns the size of the list
    // size does not imply capacity
    public int GetSize()
    {
        Node<Type> n = this.getHead();
        int i = 0;
        while (!(n == null) ){
            i++;
            n = n.getLink();
        }
        return i;

    
    }

    // inserts an item before the current element
    // the new element becomes the current
    // this should not be possible for a full list
// inserts an item before the current element
// the new element becomes the current
// this should not be possible for a full list
public void InsertBefore(Type data) {
    if (this.IsFull()) {
        return;
    }
    
    // Create the new node
    Node<Type> newNode = new Node<Type>();
    newNode.setData(data);

    // Case 1: Inserting at the head
    if (this.curr == this.head) {
        newNode.setLink(this.head);
        this.head = newNode;  // new node becomes the new head
        this.curr = this.head; // new node is the current node
    } else {
        // Case 2: General case (not at the head)
        Node<Type> prevNode = this.head;
        
        // Find the node before `curr`
        while (prevNode.getLink() != this.curr) {
            prevNode = prevNode.getLink();
        }
        
        // Insert the new node between `prevNode` and `curr`
        newNode.setLink(this.curr);
        prevNode.setLink(newNode);
        this.curr = newNode;  // new node becomes the current node
    }

    this.num_items++;
}


    // inserts an item after the current element
// the new element becomes the current
// this should not be possible for a full list
public void InsertAfter(Type data) {
    if (this.IsFull()) {
        return; // Can't insert if the list is full
    }

    // Create the new node
    Node<Type> newNode = new Node<Type>();
    newNode.setData(data);

    // Case 1: Inserting into an empty list
    if (this.IsEmpty()) {
        this.head = newNode;
        this.tail = newNode;
        this.curr = newNode; // Set curr to the new node
        return;
    }

    // Case 2: Inserting after the tail
    if (this.curr == this.tail) {
        this.curr.setLink(newNode); // Link current node to new node
        this.tail = newNode; // Update tail to new node
        this.curr = newNode; // Update curr to new node
        return;
    }

    // Case 3: Inserting in the middle
    newNode.setLink(this.curr.getLink()); // Link new node to the next node
    this.curr.setLink(newNode); // Link current node to the new node
    this.curr = newNode; // Update curr to the new node
}


    // removes the current element 
    // this should not be possible for an empty list
    // removes the current element 
// this should not be possible for an empty list
public void Remove() {
    if (this.IsEmpty()) {
        return;  // can't remove from an empty list
    }

    // Case 1: Removing the head
    if (this.curr == this.head) {
        this.head = this.curr.getLink();  // move head to the next node
        if (this.head == null) {
            this.tail = null;  // list is now empty, so update tail
        }
        this.curr = this.head;  // update curr to the new head
    } 
    // Case 2: Removing the tail
    else if (this.curr == this.tail) {
        Node<Type> prevNode = this.head;
        
        // Find the node before the tail
        while (prevNode.getLink() != this.tail) {
            prevNode = prevNode.getLink();
        }
        
        // Update tail to the previous node
        this.tail = prevNode;
        this.tail.setLink(null);
        this.curr = this.tail;  // update curr to the new tail
    } 
    // Case 3: Removing a middle node
    else {
        Node<Type> prevNode = this.head;

        // Find the node before curr
        while (prevNode.getLink() != this.curr) {
            prevNode = prevNode.getLink();
        }
        
        // Skip the current node
        prevNode.setLink(this.curr.getLink());
        this.curr = this.curr.getLink();  // update curr to the next node
    }

    this.num_items--;  // reduce the size of the list
}

    // replaces the value of the current element with the specified value
    // this should not be possible for an empty list
    public void Replace(Type data)
    {
        if (!this.IsEmpty()){
            this.curr.setData(data);
        }
    }

    // returns if the list is empty
    public boolean IsEmpty()
    {
        return (this.getHead() == null);
    }

    // returns if the list is full
    public boolean IsFull()
    {
        return (this.MAX_SIZE == this.GetSize());
    }

    // returns if two lists are equal (by value)
// returns if two lists are equal (by value)
public boolean Equals(List<Type> l) {
    // First, check if both lists are the same size
    if (this.GetSize() != l.GetSize()) {
        return false; // Not equal if sizes differ
    }
    
    Node<Type> temp1 = this.head;
    Node<Type> temp2 = l.getHead();
    
    // Compare elements in both lists
    while (temp1 != null && temp2 != null) {
        // Check for equality of the data (handle null values as necessary)
        if ((temp1.getData() == null && temp2.getData() != null) || 
            (temp1.getData() != null && !temp1.getData().equals(temp2.getData()))) {
            return false; // Not equal if data differs
        }
        
        // Move to the next nodes
        temp1 = temp1.getLink();
        temp2 = temp2.getLink();
    }
    
    return true; // Lists are equal if all elements matched
}


// returns the concatenation of two lists
// l should not be modified
// l should be concatenated to the end of *this
// the returned list should not exceed MAX_SIZE elements
// the last element of the new list is the current
public List<Type> Add(List<Type> l) {
    // Clone this list
    List<Type> newList = new List<>(this);
    
    // Navigate to the tail of the newList
    newList.Last();
    
    // Add elements of l to the new list
    Node<Type> n = l.getHead();
    while (n != null && !newList.IsFull()) {
        newList.InsertAfter(n.getData());
        n = n.getLink();
    }
    
    // Return the new concatenated list
    return newList;
}


    // returns a string representation of the entire list (e.g., 1 2 3 4 5)
    // the string "NULL" should be returned for an empty list
    public String toString()
    {
        String meep = "";
        Node<Type> nd = this.getHead();
        if (nd == null){
            return null;
        }
        while (!(nd == null)){
            meep += nd.getData();
            meep += " ";
            nd = nd.getLink();
        }
        return meep;
    }
}