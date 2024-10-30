package base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lab8Test {

    private Heap<Integer> integerHeap;

    @BeforeEach
    public void setup() {
        integerHeap = new Heap<>();
    }

    @Test
    void peek() {
        integerHeap.addAll(Arrays.asList(30, 10, 20));
        assertEquals(Integer.valueOf(10), integerHeap.peek());
    }

    @Test
    void poll() {
        var values = Arrays.asList(2, 53, 213, 5, 1, 5, 4, 210, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);

        Collections.sort(values);
        for (int x : values) {
            assertEquals(Integer.valueOf(x), integerHeap.poll());
        }
    }

    @Test
    void size() {
        var values = Arrays.asList(10, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);
        assertEquals(values.size(), integerHeap.size());
    }

    @Test
    void iter() {
        var values = Arrays.asList(2, 53, 213, 5, 1, 5, 4, 210, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        var results = Arrays.asList(1, 2, 4, 14, 5, 31, 5, 27, 53, 26, 44, 213, 35, 33, 19, 210, 52);
        integerHeap.addAll(values);
        Iterator<Integer> it = integerHeap.iterator();
        for (int x: results) {
            assertTrue(it.hasNext());
            assertEquals(it.next(), Integer.valueOf(x));
        }
    }
}
