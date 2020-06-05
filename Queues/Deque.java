public class Deque<Item> implements Iterable<Item> {
    // Instance Variables
    private class node <Item>{
        node (){
            info = null;
            next = null;
            prev = null;
        }

        Item info;
        node next;
        node prev;
    }

    private int size;
    private node head;
    private node tail;
    
    // construct an empty deque
    public Deque(){
        size = 0;
        head = new node<Item>();
        tail = head;
    }

    // is the deque empty?
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (this.isEmpty()){
            head.info = item;
        } else{
            this.head.prev = new node<Item>();
            this.head.prev.next = head;
            this.head = head.prev;
            this.head.info = item;
        }
    }

    // add the item to the back
    public void addLast(Item item){
        if (this.isEmpty()){
            this.tail.info = item;
        } else{
            this.tail.next = new node<Item>();
            this.tail.next.prev = tail;
            this.tail = tail.next;
            this.tail.info = item;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        
    }

    // remove and return the item from the back
    public Item removeLast(){

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){

    }

    // unit testing (required)
    public static void main(String[] args){

    }

}