package base.rank;
import base.Order;
public class CustomerPriorityRank implements PendingOrderRank{
    @Override
    public int compare(Order source, Order target){return source.getCustomer().getCustomerType().compareTo(target.getCustomer().getCustomerType());}
}