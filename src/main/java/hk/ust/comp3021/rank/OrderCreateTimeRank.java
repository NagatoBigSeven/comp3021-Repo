package hk.ust.comp3021.rank;
import hk.ust.comp3021.Order;
public class OrderCreateTimeRank implements PendingOrderRank{
    @Override
    public int compare(Order source, Order target){return source.getCreateTime().compareTo(target.getCreateTime());}
}
