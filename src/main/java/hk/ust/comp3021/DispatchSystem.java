package hk.ust.comp3021;
import hk.ust.comp3021.rank.PendingOrderRank;
import hk.ust.comp3021.rank.CustomerPriorityRank;
import hk.ust.comp3021.rank.OrderCreateTimeRank;
import hk.ust.comp3021.rank.RestaurantToCustomerDistanceRank;
import hk.ust.comp3021.rank.TaskRank;
import hk.ust.comp3021.rank.RiderToRestaurantRank;
import hk.ust.comp3021.rank.RiderRatingRank;
import hk.ust.comp3021.rank.RiderMonthTaskCountRank;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
public class DispatchSystem{
    /// The singleton you will use in the project.
    private static volatile DispatchSystem dispatchSystem;
    /// The field represents the current time stamp, we assume the current time stamp is 3600 seconds.
    private Long currentTimestamp = 3600L;
    /// The list stores the dishes parsed from the file.
    private List<Dish> availableDishes;
    /// The list stores the orders parsed from the file.
    private List<Order> availableOrders;
    /// The list stores the orders that is dispatched this time, and the orders should have a non-null rider field and calculated estimated time.
    private List<Order> dispatchedOrders;
    /// Task 1: Implement the constructor of the singleton pattern for the DispatchSystem class.
    /// Hint: Check if the dispatchSystem is null or not null, skip it when not null. Initialize the fields.
    private DispatchSystem(){
        if(dispatchSystem == null){
            availableDishes = new ArrayList<>();
            availableOrders = new ArrayList<>();
            dispatchedOrders = new ArrayList<>();
        }
    }
    /// Task 1: Implement the getInstance() method for the singleton pattern.
    /// Hint: Check if the dispatchSystem is null or not null and create a new instance here.
    public static DispatchSystem getInstance(){
        if(dispatchSystem == null)dispatchSystem = new DispatchSystem();
        return dispatchSystem;
    }
    public Dish getDishById(Long id){
        for(Dish availabledish: availableDishes)if(availabledish.getId().equals(id))return availabledish;
        return null;
    }
    public Boolean checkDishesInRestaurant(Restaurant restaurant, Long[] dishIds) {
        for(Long dishId : dishIds){
            Dish dish = getDishById(dishId);
            if(!dish.getRestaurantId().equals(restaurant.getId()))return false;
        }
        return true;
    }
    /// Task 2: Implement the parseAccounts() method to parse the accounts from the file.
    /// Hint: Do not forget to register the accounts into the static manager.
    public void parseAccounts(String fileName) throws IOException{
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            // Read the file and parse the accounts.
            String line;
            while((line = bufferedReader.readLine()) != null){
                if(line.isEmpty())continue;
                String[] fields = line.split(",");
                if(fields.length < 2)throw new IOException("The account file is not well formatted!");
                for(int i = 0; i < fields.length; i++)fields[i] = fields[i].trim();
                Long id = Long.valueOf(fields[0]);
                String accountType = fields[1];
                String name = fields[2];
                String contactNumber = fields[3];
                String[] pos = fields[4].substring(1, fields[4].length() - 1).split(" ");
                Double latitude = Double.valueOf(pos[0]);
                Double altitude = Double.valueOf(pos[1]);
                Location location = new Location(latitude, altitude);
                if(accountType.equals(Constants.ACCOUNT_CUSTOMER)){
                    Integer customerType = Integer.valueOf(fields[5]);
                    String gender = fields[6];
                    String email = fields[7];
                    Customer customer = new Customer(id, accountType, name, contactNumber, location, customerType, gender, email);
                    customer.register();
                }
                else if(accountType.equals(Constants.ACCOUNT_RESTAURANT)){
                    String district = fields[5];
                    String street = fields[6];
                    Restaurant restaurant = new Restaurant(id, accountType, name, contactNumber, location, district, street);
                    restaurant.register();
                }
                else{
                    String gender = fields[5];
                    Integer status = Integer.valueOf(fields[6]);
                    Double userRating = Double.valueOf(fields[7]);
                    Integer monthTaskCount = Integer.valueOf(fields[8]);
                    Rider rider = new Rider(id, accountType, name, contactNumber, location, gender, status, userRating, monthTaskCount);
                    rider.register();
                }
            }
        }
    }
    /// Task 3: Implement the parseDishes() method to parse the dishes from the file.
    /// Hint: Do not forget to add the dishes to the corresponding restaurant and the availableDishes list.
    public void parseDishes(String fileName) throws IOException{
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            // Read the file and parse the dishes.
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                String[] fields = line.split(",");
                if(fields.length < 2)throw new IOException("The dish file is not well formatted!");
                for(int i = 0; i < fields.length; i++)fields[i] = fields[i].trim();
                Long id = Long.valueOf(fields[0]);
                String name = fields[1];
                String desc = fields[2];
                BigDecimal price = new BigDecimal(fields[3]);
                Long restaurantId = Long.valueOf(fields[4]);
                Dish dish = new Dish(id, name, desc, price, restaurantId);
                Restaurant restaurant = Restaurant.getRestaurantById(restaurantId);
                restaurant.addDish(dish);
                availableDishes.add(dish);
            }
        }
    }
    /// Task 4: Implement the parseOrders() method to parse the orders from the file.
    /// Hint: Do not forget to add the order to the availableOrders list and check if the dishes ordered are in the same restaurant, skip the record if not. You can use getDishById(), checkDishesInRestaurant(), and etc. here.
    public void parseOrders(String fileName) throws IOException{
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            // Read the file and parse the orders.
            String line;
            while((line = bufferedReader.readLine()) != null && !line.isEmpty()){
                String[] fields = line.split(",");
                if (fields.length < 2)throw new IOException("The order file is not well formatted!");
                for (int i = 0; i < fields.length; i++)fields[i] = fields[i].trim();
                Long id = Long.valueOf(fields[0]);
                Integer status = Integer.valueOf(fields[1]);
                Long restaurantId = Long.valueOf(fields[2]);
                Restaurant restaurant = Restaurant.getRestaurantById(restaurantId);
                Long customerId = Long.valueOf(fields[3]);
                Customer customer = Customer.getCustomerById(customerId);
                Long createTime = Long.valueOf(fields[4]);
                Boolean isPayed = fields[5].equals("1");
                String[] orderedDishIdStrings = fields[6].substring(1, fields[6].length() - 1).split(" ");
                Long[] orderedDishIds = new Long[orderedDishIdStrings.length];
                for(int i = 0; i < orderedDishIdStrings.length; ++i)orderedDishIds[i] = Long.valueOf(orderedDishIdStrings[i]);
                List<Dish> orderedDishes = new ArrayList<>();
                for(Long orderedDishId: orderedDishIds)orderedDishes.add(getDishById(orderedDishId));
                if(!checkDishesInRestaurant(restaurant, orderedDishIds))return;
                Long riderId = fields[7].equals("NA") ? null : Long.valueOf(fields[7]);
                Rider rider = Rider.getRiderById(riderId);
                Order order = new Order(id, status, restaurant, customer, createTime, isPayed, orderedDishes, rider);
                availableOrders.add(order);
            }
        }
    }
    /// Task 5: Implement the getAvailablePendingOrders() method to get the available pending orders.
    /// Hint: The available pending orders should have the status of PENDING_ORDER, is payed, and the rider is null.
    public List<Order> getAvailablePendingOrders(){
        List<Order> availablePendingOrders = new ArrayList<>();
        for(Order availableOrder: availableOrders)if(availableOrder.getStatus().equals(Constants.PENDING_ORDER) && availableOrder.getIsPayed() && availableOrder.getRider() == null)availablePendingOrders.add(availableOrder);
        return availablePendingOrders;
    }
    /// Task 6: Implement the getRankedPendingOrders() method to rank the pending orders.
    /// Hint: Use the comparators you defined before, and sort the pending orders in order of the customer type (Top priority), order creation time (Second priority), and restaurant to customer distance (Least priority).
    public List<Order> getRankedPendingOrders(List<Order> pendingOrders) {
        PendingOrderRank customerPriorityRank = new CustomerPriorityRank();
        PendingOrderRank orderCreateTimeRank = new OrderCreateTimeRank();
        PendingOrderRank restaurantToCustomerDistanceRank = new RestaurantToCustomerDistanceRank();
        pendingOrders.sort(customerPriorityRank.thenComparing(orderCreateTimeRank).thenComparing(restaurantToCustomerDistanceRank));
        return pendingOrders;
    }
    /// Task 7: Implement the getAvailableRiders() method to get the available riders to dispatch.
    /// Hint: The available riders should have the status of RIDER_ONLINE_ORDER.
    public List<Rider> getAvailableRiders(){
        List<Rider> riders = Account.getAccountManager().getRegisteredRiders();
        List<Rider> availableRiders = new ArrayList<>();
        for(Rider rider: riders)if(rider.getStatus().equals(Constants.RIDER_ONLINE_ORDER))availableRiders.add(rider);
        return availableRiders;
    }
    /// Task 8: Implement the matchTheBestTask() method to choose the best rider for the order.
    /// Hint: The best rider should have the highest rank ranked in order of the distance between the rider and the restaurant (Top priority), the rider's user rating (Second priority), and the rider's month task count (Least priority).
    /// Use the comparators you defined before, you will also use the Task class here and the availableRiders here should be the currently available riders.
    public Task matchTheBestTask(Order order, List<Rider> availableRiders){
        List<Task> tasks = new ArrayList<>();
        for(Rider availableRider: availableRiders){
            Task task = new Task(order, availableRider);
            tasks.add(task);
        }
        TaskRank riderToRestaurantRank = new RiderToRestaurantRank();
        TaskRank riderRatingRank = new RiderRatingRank();
        TaskRank riderMonthTaskCountRank = new RiderMonthTaskCountRank();
        tasks.sort(riderToRestaurantRank.thenComparing(riderRatingRank).thenComparing(riderMonthTaskCountRank));
        return tasks.get(0);
    }
    /// Task 9: Implement the dispatchFirstRound() method to dispatch the first round of orders.
    /// Hint: The strategy is that we assign the best rider to the orders ranked one by one until the orders or riders list is empty.
    /// Do not forget to 1. remove the dispatched rider every iteration, 2. change the status of the order and the rider after the order is dispatched, and 3. calculate the estimated time for the order.
    public void dispatchFirstRound(){
        List<Order> rankedPendingOrders = getRankedPendingOrders(getAvailablePendingOrders());
        while(!rankedPendingOrders.isEmpty()){
            List<Rider> availableRiders = getAvailableRiders();
            if(availableRiders.isEmpty())return;
            Task task = matchTheBestTask(rankedPendingOrders.get(0), availableRiders);
            Order order = task.getOrder();
            Rider rider = task.getRider();
            order.setStatus(Constants.DISPATCHED_ORDER);
            order.setRider(rider);
            order.setEstimatedTime(order.calculateEstimatedTime());
            rider.setStatus(Constants.RIDER_DELIVERING);
            dispatchedOrders.add(order);
            rankedPendingOrders.remove(order);
        }
    }
    /// Do not modify the method. You should use the method to output orders for us to check the correctness of your implementation.
    public void writeOrders(String fileName, List<Order> orders) throws IOException {
        List<Order> orderedOrders = orders.stream().sorted(new Comparator<>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Order order : orderedOrders) {
                bufferedWriter.write(order.getId() + ", " + order.getStatus() + ", " + order.getRestaurant() + ", "
                        + order.getCustomer() + ", " + order.getCreateTime() + ", " + order.getIsPayed() + ", " +
                        order.getOrderedDishes() + ", " + order.getRider() + ", " + String.format("%.4f", order.getEstimatedTime()) + "\n");
            }
        }
    }
    /// Do not modify the method.
    public void writeAccounts(String fileName, List<Account> accounts) throws IOException {
        List<Account> orderedAccounts = accounts.stream().sorted(new Comparator<>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Account account : orderedAccounts) {
                bufferedWriter.write(account.toString() + "\n");
            }
        }
    }
    /// Do not modify the method.
    public void writeDishes(String fileName, List<Dish> dishes) throws IOException {
        List<Dish> orderedDishes = dishes.stream().sorted(new Comparator<>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Dish dish : orderedDishes) {
                bufferedWriter.write(dish.getId() + ", " + dish.getName() + ", " + dish.getDesc() + ", "
                        + dish.getPrice() + ", " + dish.getRestaurantId() + "\n");
            }
        }
    }
    /// Task 10: Implement the getTimeoutDispatchedOrders() method to get the timeout dispatched orders.
    /// Hint: Do not forget to take the current time stamp into consideration.
    public List<Order> getTimeoutDispatchedOrders(){
        List<Order> timeoutDispatchedOrders = new ArrayList<>();
        for(Order dispatchedOrder: dispatchedOrders){
            if(currentTimestamp + dispatchedOrder.getEstimatedTime() - dispatchedOrder.getCreateTime() > Constants.DELIVERY_TIME_LIMIT){
                timeoutDispatchedOrders.add(dispatchedOrder);
            }
        }
        return timeoutDispatchedOrders;
    }
    /// Do not modify the method.
    public List<Order> getAvailableOrders(){return availableOrders;}
    /// Do not modify the method.
    public List<Order> getDispatchedOrders(){return dispatchedOrders;}
    /// Do not modify the method.
    public List<Account> getAccounts(){
        Account.AccountManager manager = Account.getAccountManager();
        return manager.getRegisteredAccounts();
    }
    /// Do not modify the method.
    public List<Dish> getDishes(){return availableDishes;}
    /// Finish the main method to test your implementation.a
    public static void main(String[] args){
        try{
            getInstance().parseAccounts("Accounts.txt");
            getInstance().parseDishes("Dishes.txt");
            getInstance().parseOrders("Orders.txt");
            getInstance().writeOrders("availableOrders.txt", getInstance().availableOrders);
            getInstance().dispatchFirstRound();
            getInstance().writeOrders("firstRoundDispatchedOrders.txt", getInstance().dispatchedOrders);
            List<Order> timeoutOrders = getInstance().getTimeoutDispatchedOrders();
            getInstance().writeOrders("timeoutDispatchedOrders.txt", timeoutOrders);
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
