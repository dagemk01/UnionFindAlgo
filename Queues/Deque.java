import java.util.*;
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
        head = new node();
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
        if (this.isEmpty()){
            throw new NoSuchElementException ("Removing an empty Deque!");
        }
        --this.size;
        if (size == 1){
            return (Item) head.info;
            
        }

        node temp = head;
        head = head.next;
        temp.next = null;
        head.prev = null;

        return (Item) temp.info;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (this.isEmpty()){
            throw new NoSuchElementException ("Removing an empty Deque!");
        }
        --size;
        if (size == 1){
            return (Item) tail.info;
        }
        node temp = tail;
        tail = tail.prev;
        temp.prev = null;
        tail.next = null;


        return (Item) temp.info;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>{

        private node current = head;
        @Override
        public boolean hasNext() {
            
            if (current == null){
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            
            Item item = (Item) current.info;
            current = current.next;
            
            return item;
        }
        
    }
    // unit testing (required)
    public static void main(String[] args){

    }

}