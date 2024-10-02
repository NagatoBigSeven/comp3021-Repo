package base.rank;
import base.Task;
public class RiderToRestaurantRank implements TaskRank{
    @Override
    public int compare(Task source, Task target){return source.getRider().getLocation().distanceTo(source.getOrder().getRestaurant().getLocation()).compareTo(target.getRider().getLocation().distanceTo(target.getOrder().getRestaurant().getLocation()));}
}
