package base;
import java.util.List;
public class Order{
    private Long id;
    private Integer status;
    private Restaurant restaurant;
    private Customer customer;
    private Long createTime;
    private Boolean isPayed;
    private List<Dish> orderedDishes;
    private Rider rider;
    private Double estimatedTime;
    public Order(Long id, Integer status, Restaurant restaurant, Customer customer, Long createTime, Boolean isPayed, List<Dish> orderedDishes, Rider rider){
        this.id = id;
        this.status = status;
        this.restaurant = restaurant;
        this.customer = customer;
        this.createTime = createTime;
        this.isPayed = isPayed;
        this.orderedDishes = orderedDishes;
        this.rider = rider;
    }
    public Long getId(){return id;}
    public Integer getStatus(){return status;}
    public void setStatus(Integer status){this.status = status;}
    public Restaurant getRestaurant(){return restaurant;}
    public Customer getCustomer(){return customer;}
    public Long getCreateTime(){return createTime;}
    public Boolean getIsPayed(){return isPayed;}
    public List<Dish> getOrderedDishes(){return orderedDishes;}
    public Rider getRider(){return rider;}
    public void setRider(Rider rider){this.rider = rider;}
    public Double getEstimatedTime(){return estimatedTime;}
    public void setEstimatedTime(Double estimatedTime){this.estimatedTime = estimatedTime;}
    public Double calculateEstimatedTime(){
        Location riderLocation = rider.getLocation();
        Location restaurantLocation = restaurant.getLocation();
        Location customerLocation = customer.getLocation();
        return (riderLocation.distanceTo(restaurantLocation) + restaurantLocation.distanceTo(customerLocation)) / Constants.DELIVERY_SPEED;
    }
}
