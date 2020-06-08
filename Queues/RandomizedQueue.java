import java.util.*;

import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    //Instance variables
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

    // construct an empty randomized queue
    public RandomizedQueue(){
        size = 0;
        head = new node();
        tail = head;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if (this.isEmpty()){
            head.info = item;
        } else{
            this.head.prev = new node<Item>();
            this.head.prev.next = head;
            this.head = head.prev;
            this.head.info = item;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue(){
        if (size == 0){
            throw new NoSuchElementException("Array Empty");
        }
        if (size == 1){
            --size;
            return (Item) head.info;
        }
        --size;
        
        node remove = find();


        if (remove.prev == head){
            head = head.next;
            head.prev = null;
            remove.next = null;

            return (Item) remove.info;
        }

        if (remove.prev == tail){
            tail = tail.prev;
            tail.next = null;
            remove.prev = null;

            return (Item) remove.info;
        }
        
        remove.prev.next = remove.next;
        remove.next.prev = remove.prev;
        remove.next = null;
        remove.prev = null;
        
        return (Item) remove.info;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        node randNode = find();

        return (Item) randNode.info;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new randIterator();
    }

    private class randIterator implements Iterator<Item>{
        node current = head;
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

    // Private helper function
    private node find(){
        int index = StdRandom.uniform(0, size);
        node curr = head;
        for (int i = 1; i <= index && curr != null; i ++){
            curr = curr.next;
        }
        return curr;
    }
    // unit testing (required)
    public static void main(String[] args){

    }
}