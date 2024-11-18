package base;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static org.junit.jupiter.api.Assertions.*;
public class Lab11Test {
    @RepeatedTest(50)
    @Timeout(value = 1100, unit = TimeUnit.MILLISECONDS)
    public void testTwoThreads() throws InterruptedException {
        var segments = new String[]{"hlowrd", "el ol!"};
        var result = new StringBuilder();
        var threads = new ArrayList<Thread>();
        var lock = new ReentrantLock();
        ParallelMerger.merge(segments, ch -> {
            lock.lock();
            var threadIdx = result.length() % segments.length;
            result.append(ch);
            var thread = Thread.currentThread();
            if (!threads.contains(thread)) {
                threads.add(thread);
            }
            // check whether threads take turns to write
            assertEquals(threads.get(threadIdx), thread);
            lock.unlock();
        });
        Thread.sleep(1); // tolerance for thread pool to terminate
        for (var thread :
                threads) {
            // check whether all threads have exited after merge() returns
            assertEquals(Thread.State.TERMINATED, thread.getState());
        }
        // check whether number of threads is the same as number of segments to merge.
        assertEquals(2, threads.size());
        // check merge result
        assertEquals("hello world!", result.toString());
    }
    @RepeatedTest(50)
    @Timeout(value = 1100, unit = TimeUnit.MILLISECONDS)
    public void testThreeThreads() throws InterruptedException {
        var segments = new String[]{"hlwl", "eood", "l r!"};
        var result = new StringBuilder();
        var threads = new ArrayList<Thread>();
        var lock = new ReentrantLock();
        ParallelMerger.merge(segments, ch -> {
            lock.lock();
            var threadIdx = result.length() % segments.length;
            result.append(ch);
            var thread = Thread.currentThread();
            if (!threads.contains(thread)) {
                threads.add(thread);
            }
            // check whether threads take turns to write
            assertEquals(threads.get(threadIdx), thread);
            lock.unlock();
        });
        Thread.sleep(1); // tolerance for thread pool to terminate
        for (var thread :
                threads) {
            // check whether all threads have exited after merge() returns
            assertEquals(Thread.State.TERMINATED, thread.getState());
        }
        // check whether number of threads is the same as number of segments to merge.
        assertEquals(3, threads.size());
        // check merge result
        assertEquals("hello world!", result.toString());
    }
}