package base.rank;
import base.Order;
import java.util.Comparator;
public interface PendingOrderRank extends Comparator<Order>{
    @Override
    int compare(Order source, Order target);
}