package hk.ust.comp3021;
import java.util.*;
import java.util.function.Consumer;

public class ParEvaluator<T> implements Evaluator<T>{
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();
  private TaskPool pool;
  public ParEvaluator(int numThreads){
    pool = new TaskPool(numThreads);
  }
  public void addDependency(FunNode<T> a, FunNode<T> b, int i){
    // part 4: parallel function evaluator
    listeners.computeIfAbsent(a, node -> new ArrayList<>());
    listeners.get(a).add(value -> {
      final Optional<FunNode<T>> optional = b.setInput(i, value);
      optional.ifPresent(node -> pool.addTask(() -> {
        node.eval();
        if(listeners.get(node) != null){
          listeners.get(node).forEach(listener -> listener.accept(node.getResult()));
        }
      }));
    });
    //throw new UnsupportedOperationException();
  }
  public void terminate(){
    pool.terminate();
  }
  public void start(List<FunNode<T>> nodes){
    // part 4: parallel function evaluator
    final List<Runnable> tasks = nodes.stream().map(node -> (Runnable)(() -> {
      node.eval();
      if(listeners.get(node) != null){
        listeners.get(node).forEach(listener -> listener.accept(node.getResult()));
      }
    })).toList();
    pool.addTasks(tasks);
    //throw new UnsupportedOperationException();
  }
}