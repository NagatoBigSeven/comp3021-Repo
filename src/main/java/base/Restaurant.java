package base;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
public class Restaurant extends Account {
    private String district;
    private String street;
    private List<Dish> dishes;
    public Restaurant(Long id, String accountType, String name, String contactNumber, Location location, String district, String street){
        super(id, accountType, name, contactNumber, location);
        this.district = district;
        this.street = street;
        this.dishes = new ArrayList<Dish>();
    }
    /// Do not modify this method.
    @Override
    public String toString() {
        List<Long> dishIds = new LinkedList<>(dishes.stream().map(Dish::getId).toList());
        dishIds.sort(Long::compareTo);
        return "Restaurant{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", dishIds='" + dishIds + '\'' +
                '}';
    }
    public void addDish(Dish dish){dishes.add(dish);}
    @Override
    public void register(){accountManager.addRestaurant(this);}
    public static Restaurant getRestaurantById(Long id){return accountManager.getRestaurantById(id);}
}
