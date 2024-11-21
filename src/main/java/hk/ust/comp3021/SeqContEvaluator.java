package hk.ust.comp3021;
import java.util.*;
import java.util.function.Consumer;
public class SeqContEvaluator<T> implements Evaluator<T>{
  private ArrayDeque<FunNode<T>> toEval = new ArrayDeque<>();
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();
  public void addDependency(FunNode<T> a, FunNode<T> b, int i){
    // part 2: sequential function evaluator
    listeners.computeIfAbsent(a, node -> new ArrayList<>());
    listeners.get(a).add(value -> {
      final Optional<FunNode<T>> optional = b.setInput(i, value);
      optional.ifPresent(node -> toEval.add(node));
    });
    //throw new UnsupportedOperationException();
  }
  public void start(List<FunNode<T>> nodes){
    // part 2: sequential function evaluator
    toEval.addAll(nodes);
    while(!toEval.isEmpty()){
      final FunNode<T> node = toEval.poll();
      node.eval();
      if(listeners.get(node) != null){
        listeners.get(node).forEach(listener -> listener.accept(node.getResult()));
      }
    }
    //throw new UnsupportedOperationException();
  }
}