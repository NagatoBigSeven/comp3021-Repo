package hk.ust.comp3021;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class EvaluatorTest {
  static List<Evaluator<Object>> evaluators() {
    return List.of(new SeqEvaluator<>(), new SeqContEvaluator<>(),
                   new ParEvaluator<>(4));
  }

  @ParameterizedTest
  @MethodSource("evaluators")
  void dag(Evaluator<Integer> evaluator) {
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
    evaluator.addDependency(a, b, 0);
    evaluator.addDependency(a, c, 1);
    evaluator.addDependency(b, c, 0);
    evaluator.start(List.of(a));
    assert c.getResult() == 9;
  }

  @ParameterizedTest
  @MethodSource("evaluators")
  void sumArrays(Evaluator<int[]> evaluator) {
    final int SIZE = 42000;
    Function<List<int[]>, int[]> id = list -> list.get(0);
    Function<List<int[]>, int[]> sum = list -> {
      var result = new int[list.get(0).length];
      try {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        var buffer = ByteBuffer.allocate(8);
        // honestly, I have no idea what this does, I only know it is slow
        for (int i = 0; i < result.length; i++) {
          buffer.putInt(list.get(0)[i]);
          buffer.putInt(list.get(1)[i]);
          var r = ByteBuffer.wrap(digest.digest(buffer.array()));
          buffer.clear();
          result[i] = 0;
          while (r.hasRemaining())
            result[i] += r.getInt();
        }
      } catch (NoSuchAlgorithmException ex) {
      }
      return result;
    };

    for (int iterations = 0; iterations < 5; iterations++) {
      var inputs = new ArrayList<int[]>();
      for (int i = 0; i < 64; i++) {
        var arr = new int[SIZE];
        for (int j = 0; j < arr.length; j++) {
          arr[j] = (j + i) % 42;
        }
        inputs.add(arr);
      }
      var idNodes = new ArrayList<FunNode<int[]>>();
      var layer = new ArrayList<FunNode<int[]>>();
      // add id layers
      for (int i = 0; i < inputs.size(); i++) {
        var node = new FunNode<>(1, id);
        node.setInput(0, inputs.get(i));
        layer.add(node);
        idNodes.add(node);
      }
      while (layer.size() > 1) {
        var oldLayer = new ArrayList<>(layer);
        layer.clear();
        for (int i = 0; i < oldLayer.size(); i += 2) {
          if (i == oldLayer.size() - 1) {
            layer.add(oldLayer.get(i));
            continue;
          }
          var node = new FunNode<>(2, sum);
          evaluator.addDependency(oldLayer.get(i), node, 0);
          evaluator.addDependency(oldLayer.get(i + 1), node, 1);
          layer.add(node);
        }
      }
      evaluator.start(idNodes);
    }
  }
}
