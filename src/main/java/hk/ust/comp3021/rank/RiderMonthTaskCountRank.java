package hk.ust.comp3021.rank;
import hk.ust.comp3021.Task;
public class RiderMonthTaskCountRank implements TaskRank{
    @Override
    public int compare(Task source, Task target){return source.getRider().getMonthTaskCount().compareTo(target.getRider().getMonthTaskCount());}
}
