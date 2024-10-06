package hk.ust.comp3021.rank;
import hk.ust.comp3021.Task;
public class RiderRatingRank implements TaskRank{
    @Override
    public int compare(Task source, Task target){return -source.getRider().getUserRating().compareTo(target.getRider().getUserRating());}
}
