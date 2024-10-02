package base.rank;
import base.Task;
public class RiderRatingRank implements TaskRank{
    @Override
    public int compare(Task source, Task target){return -source.getRider().getUserRating().compareTo(target.getRider().getUserRating());}
}
