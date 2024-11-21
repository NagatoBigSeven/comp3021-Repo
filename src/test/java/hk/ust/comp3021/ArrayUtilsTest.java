package hk.ust.comp3021;

import java.util.function.IntUnaryOperator;
import org.junit.jupiter.api.Test;

public class ArrayUtilsTest {
  // complex map function to make things slow, so parallelization make sense
  // (instead of memory bandwidth limited)
  final static IntUnaryOperator complexMap = x -> {
    for (int i = 0; i < 10; i++)
      x = Integer.hashCode(x);
    return x;
  };

  @Test
  public void benchSeqMap() {
    int[] input = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = i;
    for (int iteration = 0; iteration < 100; iteration++)
      input = ArrayUtils.seqMap(input, complexMap);
  }

  @Test
  public void benchParMap() {
    TaskPool pool = new TaskPool(4);
    int[] input = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = i;
    for (int iteration = 0; iteration < 100; iteration++)
      input = ArrayUtils.parMap(input, complexMap, pool);
  }

  @Test
  public void testMap() {
    TaskPool pool = new TaskPool(4);
    int[] input = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = i;
    var a = ArrayUtils.seqMap(input, x -> x * 2);
    var b = ArrayUtils.parMap(input, x -> x * 2, pool);
    for (int i = 0; i < input.length; i++)
      assert a[i] == b[i];
  }

  @Test
  public void benchSeqPrefixSum() {
    int[] input = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = i;
    for (int iteration = 0; iteration < 100; iteration++)
      ArrayUtils.seqInclusivePrefixSum(input, (x, y) -> x + y);
  }

  @Test
  public void benchParPrefixSum() {
    TaskPool pool = new TaskPool(4);
    int[] input = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = i;
    for (int iteration = 0; iteration < 100; iteration++)
      ArrayUtils.parInclusivePrefixSum(input, (x, y) -> x + y, pool);
  }

  @Test
  public void testPrefixSum() {
    TaskPool pool = new TaskPool(4);
    int[] input = new int[1 << 24];
    int[] input2 = new int[1 << 24];
    for (int i = 0; i < input.length; i++)
      input[i] = input2[i] = i;
    ArrayUtils.seqInclusivePrefixSum(input, (x, y) -> x + y);
    ArrayUtils.parInclusivePrefixSum(input2, (x, y) -> x + y, pool);
    for (int i = 0; i < input.length; i++)
      assert input[i] == input2[i];
  }
}
