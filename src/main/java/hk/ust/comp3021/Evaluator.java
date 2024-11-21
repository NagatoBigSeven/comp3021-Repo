package hk.ust.comp3021;
import java.util.List;
public interface Evaluator<T>{
  // the i-th argument of b is the result of a
  void addDependency(FunNode<T> a, FunNode<T> b, int i);
  // start evaluating nodes
  void start(List<FunNode<T>> nodes);
}