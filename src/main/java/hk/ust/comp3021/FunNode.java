package hk.ust.comp3021;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class FunNode<T>{
  private List<Optional<T>> inputs;
  private Optional<T> output = Optional.empty();
  private Function<List<T>, T> f;
  public FunNode(int arity, Function<List<T>, T> fun){
    // part 1: function data dependency graph node
    inputs = new ArrayList<>();
    IntStream.range(0, arity).forEach(i -> inputs.add(Optional.empty()));
    f = fun;
    //throw new UnsupportedOperationException();
  }
  public synchronized Optional<FunNode<T>> setInput(int i, T value){
    // part 1: function data dependency graph node
    inputs.set(i, Optional.of(value));
    final long numAvailableInputs = inputs.stream().filter(Optional::isPresent).count();
    return numAvailableInputs == inputs.size() ? Optional.of(this) : Optional.empty();
    //throw new UnsupportedOperationException();
  }
  public synchronized T getResult(){return output.get();}
  public synchronized void eval(){
    // part 1: function data dependency graph node
    output = Optional.of(f.apply(inputs.stream().map(Optional :: get).collect(Collectors.toList())));
    //throw new UnsupportedOperationException();
  }
}