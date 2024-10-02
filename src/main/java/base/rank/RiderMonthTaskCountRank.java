package base.rank;
import base.Task;
public class RiderMonthTaskCountRank implements TaskRank{
    @Override
    public int compare(Task source, Task target){return source.getRider().getMonthTaskCount().compareTo(target.getRider().getMonthTaskCount());}
}
