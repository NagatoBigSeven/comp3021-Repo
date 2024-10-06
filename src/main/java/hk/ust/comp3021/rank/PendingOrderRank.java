package hk.ust.comp3021.rank;
import hk.ust.comp3021.Order;
import java.util.Comparator;
public interface PendingOrderRank extends Comparator<Order>{
    @Override
    int compare(Order source, Order target);
}