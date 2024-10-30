package base;
import java.util.*;
/**
 * The class of a binary min heap data structure.
 * Please refer to <a href="https://en.wikipedia.org/wiki/Heap_(data_structure)">this wikipedia page</a> for more details.
 * <p>
 * Note: We want to accept any type which is comparable to itself.
 * @param <T>
 */
public class Heap<T extends Comparable<T>>{
    private final ArrayList<T> container;
    public Heap() {
        container = new ArrayList<>();
    }
    /**
     * Find a min item of a min-heap.
     *
     * @return If size is 0, throw {@link IllegalStateException}.
     * Otherwise, return the first element of {@link #container}
     */
    public T peek(){
        // TODO
        if(this.size() == 0)throw new IllegalStateException();
        return container.get(0);
    }
    /**
     * Remove the minimum item (root) of a min heap, respectively
     *
     * @return If size is 0, throw {@link IllegalStateException}. Otherwise, temporarily save the first element.
     * Afterward, set the first position to the last element, and remove the last element.
     * Call {@link #heapifyDown()}, then return the original first element
     */
    public T poll(){
        // TODO
        if(this.size() == 0)throw new IllegalStateException();
        T minimum = container.get(0);
        container.set(0, container.get(container.size() - 1));
        container.remove(container.size() - 1);
        heapifyDown();
        return minimum;
    }
    private void heapifyDown() {
        int pos = 0;
        while (hasLeft(pos)) {
            int smallerChildIndex = getLeftIndex(pos);
            if (hasRight(pos) && container.get(getRightIndex(pos)).compareTo(container.get(getLeftIndex(pos))) < 0) {
                smallerChildIndex = getRightIndex(pos);
            }
            if (container.get(pos).compareTo(container.get(smallerChildIndex)) < 0) {
                break;
            } else {
                swap(pos, smallerChildIndex);
            }
            pos = smallerChildIndex;
        }
    }
    /**
     * Add the object into {@link #container}, then call {@link #heapifyUp()}
     *
     * @param obj the object to add
     */
    public void add(T obj) {
        //TODO
        container.add(obj);
        heapifyUp();
    }
    public void addAll(Collection<T> list) {
        list.forEach(this::add);
    }
    /**
     * While the last element has a parent and is smaller than its parent, swap the two elements. Then, check again
     * with the new parent until there's either no parent or we're larger than our parent.
     * you can refer to {@link #heapifyDown()}.
     */
    private void heapifyUp() {
        // TODO
        int pos = container.size() - 1;
        while(hasParent(pos)){
            int parentIndex = getParentIndex(pos);
            if(container.get(pos).compareTo(container.get(parentIndex)) > 0){
                break;
            }
            else{
                swap(pos, parentIndex);
            }
            pos = parentIndex;
        }
    }
    public int size() {
        return container.size();
    }
    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }
    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }
    private int getRightIndex(int i) {
        return 2 * i + 2;
    }
    private boolean hasParent(int i) {
        return i != 0 && getParentIndex(i) >= 0;
    }
    private boolean hasLeft(int i) {
        return getLeftIndex(i) < container.size();
    }
    private boolean hasRight(int i) {
        return getRightIndex(i) < container.size();
    }
    private void swap(int i1, int i2) {
        Collections.swap(container, i1, i2);
    }
    /**
     * The iterator to go through the private {@link #container}.
     * Please define class HeapIterator here. it should implement interface
     * {@link Iterator<T>}.
     */
    public class HeapIterator implements Iterator<T>{
        int i = 0;
        @Override
        public boolean hasNext(){
            return i < container.size();
        }
        @Override
        public T next(){
            if(!hasNext())return null;
            return container.get(i++);
        }
    }
    // TODO
    public HeapIterator iterator() {
        return new HeapIterator();
    }
    public static void main(String[] args){
        Heap<Integer> integerHeap = new Heap<>();
        integerHeap.addAll(Arrays.asList(30, 10, 20));
        System.out.println(integerHeap.peek());
    }
}