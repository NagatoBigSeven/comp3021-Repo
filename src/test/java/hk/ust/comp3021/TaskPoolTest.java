package hk.ust.comp3021;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TaskPoolTest {
  @Test
  @Timeout(value = 1, unit = TimeUnit.SECONDS)
  public void TestSimple() {
    TaskPool pool = new TaskPool(1);
    var tasks = new ArrayList<Runnable>();
    int[] contents = new int[40];
    IntStream.range(0, 40).forEach(i -> { contents[i] = 0; });
    // try to change the following lambda into for loop and see what will happen
    IntStream.range(0, 40).forEach(i -> tasks.add(() -> {
      try {
        Thread.sleep(10);
        contents[i] = 1;
      } catch (InterruptedException e) {
        assert false;
      }
    }));
    pool.addTasks(tasks);
    IntStream.range(0, 40).forEach(i -> { assert (contents[i] == 1); });
  }

  @Test
  @Timeout(value = 1, unit = TimeUnit.SECONDS)
  public void TestSimple2() {
    TaskPool pool = new TaskPool(4);
    var tasks = new ArrayList<Runnable>();
    // try to change the following lambda into for loop and see what will happen
    IntStream.range(0, 20).forEach(i -> tasks.add(() -> {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        assert false;
      }
    }));
    pool.addTasks(tasks);
  }

  @Test
  @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
  public void TestTerminate() {
    TaskPool pool = new TaskPool(4);
    var tasks = new ArrayList<Runnable>();
    IntStream.range(0, 40).forEach(i -> tasks.add(() -> {
      pool.addTask(() -> {
        try {
          Thread.sleep(10000000);
        } catch (InterruptedException e) {
        }
      });
    }));
    var t = new Thread(() -> pool.addTasks(tasks));
    t.start();
    try {
      Thread.sleep(100);
      pool.terminate();
      t.join();
    } catch (InterruptedException ex) {
      assert false;
    }
  }
}
