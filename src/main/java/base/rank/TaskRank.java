package base.rank;
import base.Task;
import java.util.Comparator;
public interface TaskRank extends Comparator<Task>{
    @Override
    int compare(Task source, Task target);
}
