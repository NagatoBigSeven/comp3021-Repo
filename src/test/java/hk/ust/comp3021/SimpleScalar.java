package hk.ust.comp3021;

import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

public class SimpleScalar {
  @Test
  void unary() {
    var a = new FunNode<Integer>(1, list -> list.get(0) + 1);
    assert a.setInput(0, 1).isPresent();
    a.eval();
    assert a.getResult() == 2;
  }

  @Test
  void binary() {
    var a = new FunNode<Integer>(2, list -> list.get(0) + list.get(1));
    assert !a.setInput(0, 1).isPresent();
    assert a.setInput(1, 2).isPresent();
    a.eval();
    assert a.getResult() == 3;
  }

  @Test
  void dag() {
    // (x + y) * 2 + (x + y)
    // a = x + y
    // b = a * 2
    // c = b + a
    Function<List<Integer>, Integer> sum = list -> list.get(0) + list.get(1);
    var a = new FunNode<Integer>(2, sum);
    var b = new FunNode<Integer>(1, list -> list.get(0) * 2);
    var c = new FunNode<Integer>(2, sum);

    a.setInput(0, 1);
    a.setInput(1, 2);
    a.eval();
    b.setInput(0, a.getResult());
    c.setInput(1, a.getResult());
    b.eval();
    c.setInput(0, b.getResult());
    c.eval();
    assert c.getResult() == 9;
  }
}
