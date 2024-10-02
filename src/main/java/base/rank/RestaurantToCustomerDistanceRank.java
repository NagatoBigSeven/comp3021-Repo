package base.rank;
import base.Order;
public class RestaurantToCustomerDistanceRank implements PendingOrderRank{
    @Override
    public int compare(Order source, Order target){return source.getRestaurant().getLocation().distanceTo(source.getCustomer().getLocation()).compareTo(target.getRestaurant().getLocation().distanceTo(target.getCustomer().getLocation()));}
}