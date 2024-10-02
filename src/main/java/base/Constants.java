package base;
public class Constants{
    /// General constants.
    // The time limit of delivery is all 30 min, since the created time of the order.
    public static final Integer DELIVERY_TIME_LIMIT = 1800;
    // The speed of the rider is all 4 m/s.
    public static final Integer DELIVERY_SPEED = 4;
    public static final String ACCOUNT_CUSTOMER = "CUSTOMER";
    public static final String ACCOUNT_RESTAURANT = "RESTAURANT";
    public static final String ACCOUNT_RIDER = "RIDER";
    /// Customer constants.
    public static final Integer VIP_CUSTOMER = 1;
    public static final Integer NORMAL_CUSTOMER = 2;
    /// Rider constants.
    // Not ready to dispatch orders.
    public static final Integer RIDER_OFFLINE = 3;
    // Ready to dispatch orders.
    public static final Integer RIDER_ONLINE_ORDER = 4;
    // The rider is delivering, not ready to dispatch orders.
    public static final Integer RIDER_DELIVERING = 5;
    /// Order constants.
    // The order is not dispatched yet.
    public static final Integer PENDING_ORDER = 6;
    // The order is dispatched, but not completed yet.
    public static final Integer DISPATCHED_ORDER = 7;
    // The order is completed.
    public static final Integer COMPLETED_ORDER = 8;
}
