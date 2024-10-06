package hk.ust.comp3021.rank;
import hk.ust.comp3021.Order;
public class CustomerPriorityRank implements PendingOrderRank{
    @Override
    public int compare(Order source, Order target){return source.getCustomer().getCustomerType().compareTo(target.getCustomer().getCustomerType());}
}