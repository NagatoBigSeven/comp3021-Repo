package hk.ust.comp3021.rank;
import hk.ust.comp3021.Task;
import java.util.Comparator;
public interface TaskRank extends Comparator<Task>{
    @Override
    int compare(Task source, Task target);
}
