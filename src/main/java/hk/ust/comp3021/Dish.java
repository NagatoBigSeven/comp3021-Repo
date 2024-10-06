package hk.ust.comp3021;
import java.math.BigDecimal;
public class Dish{
    private Long id;
    private String name, desc;
    private BigDecimal price;
    private Long restaurantId;
    Dish(Long id, String name, String desc, BigDecimal price, Long restaurantId){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.restaurantId = restaurantId;
    }
    @Override
    public String toString(){return "Dish(id=" + id + ", name=" + name + ", desc=" + desc + ", price=" + price + ", restaurantId=" + restaurantId + ")";}
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getDesc(){return desc;}
    public BigDecimal getPrice(){return price;}
    public Long getRestaurantId(){return restaurantId;}
}
