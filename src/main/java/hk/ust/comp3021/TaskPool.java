package hk.ust.comp3021;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
public class TaskPool{
  public class TaskQueue{
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private boolean terminated = false;
    private int working;
    private Semaphore idle;
    public TaskQueue(int numThreads, Semaphore idle){
      working = numThreads;
      this.idle = idle;
    }
    public synchronized Optional<Runnable> getTask(){
      // part 3: task pool
      if(terminated){
        --working;
        if(working == 0){
          idle.release();
        }
        return Optional.empty();
      }
      if(queue.isEmpty()){
        --working;
        if(working == 0){
          idle.release();
        }
        while(queue.isEmpty()){
          try{
            wait();
            if(terminated){
              return Optional.empty();
            }
          }
          catch(InterruptedException e){
            return Optional.empty();
          }
        }
        ++working;
      }
      return Optional.of(queue.poll());
      //throw new UnsupportedOperationException();
    }
    public synchronized void addTask(Runnable task){
      // part 3: task pool
      if(terminated)return;
      while(idle.availablePermits() > 0) {
        try {
          idle.acquire();
        } catch (InterruptedException ignored) {
        }
      }
      queue.offer(task);
      notifyAll();
      //throw new UnsupportedOperationException();
    }
    public void terminate(){
      // part 3: task pool
      terminated = true;
      //throw new UnsupportedOperationException();
    }
  }
  private TaskQueue queue;
  private Thread[] workers;
  private Semaphore idle = new Semaphore(0);
  public TaskPool(int numThreads){
    // part 3: task pool
    queue = new hk.ust.comp3021.TaskPool.TaskQueue(numThreads, idle);
    workers = new Thread[numThreads];
    IntStream.range(0, numThreads).forEach(i -> {
      workers[i] = new Thread(() -> {
        while(true){
          final Optional<Runnable> optional = queue.getTask();
          if(optional.isEmpty()){
            break;
          }
          final Runnable task = optional.get();
          task.run();
        }
      });
      workers[i].start();
    });
    //throw new UnsupportedOperationException();
  }
  public void addTask(Runnable task){queue.addTask(task);}
  public void addTasks(List<Runnable> tasks){
    // part 3: task pool
    IntStream.range(0, tasks.size()).forEach(i -> addTask(tasks.get(i)));
    try{
      idle.acquire(1);
      idle.release(1);
    }
    catch(InterruptedException ignored){

    }
    //throw new UnsupportedOperationException();
  }
  public synchronized void terminate() {
    queue.terminate();
    for (Thread thread : workers) {
      try {
        // this will send an InterruptedException to the thread to wake it up
        // from blocking operations such as Thread.sleep.
        if (thread.isAlive()){
          thread.interrupt();
        }
        thread.join();
      } catch (InterruptedException ignored) {}
    }
  }
}