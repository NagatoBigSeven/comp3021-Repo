package base.rank;
import base.Order;
public class OrderCreateTimeRank implements PendingOrderRank{
    @Override
    public int compare(Order source, Order target){return source.getCreateTime().compareTo(target.getCreateTime());}
}
