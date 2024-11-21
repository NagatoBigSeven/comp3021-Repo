package hk.ust.comp3021;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
public class ArrayUtils{
  // HINT: try to use this chunk size to partition your work.
  static final int CHUNK_SIZE = 1 << 16;
  public static int[] seqMap(int[] input, IntUnaryOperator map) {
    int[] output = new int[input.length];
    IntStream.range(0, input.length).forEach(i -> output[i] = map.applyAsInt(input[i]));
    return output;
  }
  public static int[] parMap(int[] input, IntUnaryOperator map, TaskPool pool){
    // Bonus part
    int[] output = new int[input.length];
    List<Runnable> tasks = IntStream.range(0, input.length / CHUNK_SIZE).boxed().map(i -> (Runnable)() -> IntStream.range(i * CHUNK_SIZE, (i + 1) * CHUNK_SIZE).forEach(j -> output[j] = map.applyAsInt(input[j]))).toList();
    pool.addTasks(tasks);
    return output;
    //throw new UnsupportedOperationException();
  }
  public static void seqInclusivePrefixSum(int[] input, IntBinaryOperator op){
    IntStream.range(1, input.length).forEach(i -> input[i] = op.applyAsInt(input[i - 1], input[i]));
  }
  public static void parInclusivePrefixSum(int[] input, IntBinaryOperator op, TaskPool pool){
    // Bonus part
    int[] tmp = new int[input.length / CHUNK_SIZE];
    final List<Runnable> tasks = IntStream.range(0, input.length / CHUNK_SIZE).boxed().map(i -> (Runnable)() -> IntStream.range(i * CHUNK_SIZE, (i + 1) * CHUNK_SIZE).forEach(j -> tmp[i] = op.applyAsInt(tmp[i], input[j]))).toList();
    pool.addTasks(tasks);
    IntStream.range(1, tmp.length).forEach(i -> tmp[i] = op.applyAsInt(tmp[i - 1], tmp[i]));
    final List<Runnable> tasksPrime = IntStream.range(0, input.length / CHUNK_SIZE).boxed().map(i -> (Runnable)() -> {
      if(i >= 1){
        input[i * CHUNK_SIZE] = op.applyAsInt(input[i * CHUNK_SIZE], tmp[i - 1]);
      }
      IntStream.range(i * CHUNK_SIZE + 1, (i + 1) * CHUNK_SIZE).forEach(j -> input[j] = op.applyAsInt(input[j - 1], input[j]));
    }).toList();
    pool.addTasks(tasksPrime);
    IntStream.range(input.length / CHUNK_SIZE * CHUNK_SIZE, input.length).forEach(i -> input[i] = op.applyAsInt(input[i - 1], input[i]));
    //throw new UnsupportedOperationException();
  }
}