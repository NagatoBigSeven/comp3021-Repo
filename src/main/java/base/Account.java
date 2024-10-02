package base;

import java.util.List;
import java.util.ArrayList;
public abstract class Account{
    protected Long id;
    protected String accountType;
    protected String name;
    protected String contactNumber;
    protected Location location;
    public Account(Long id, String accountType, String name, String contactNumber, Location location){
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
    }
    public Long getId(){return id;}
    public String getAccountType(){return accountType;}
    public String getName(){return name;}
    public String getContactNumber(){return contactNumber;}
    public Location getLocation(){return location;}
    /// This is where the registered accounts are stored.
    protected static class AccountManager{
        private List<Account> registeredAccounts;
        private List<Customer> registeredCustomers;
        private List<Restaurant> registeredRestaurants;
        private List<Rider> registeredRiders;
        public AccountManager(){
            registeredAccounts = new ArrayList<>();
            registeredCustomers = new ArrayList<>();
            registeredRestaurants = new ArrayList<>();
            registeredRiders = new ArrayList<>();
        }
        /// Do not modify this method.
        public List<Account> getRegisteredAccounts(){return registeredAccounts;}
        public List<Customer> getRegisteredCustomers(){return registeredCustomers;}
        public List<Restaurant> getRegisteredRestaurants(){return registeredRestaurants;}
        public List<Rider> getRegisteredRiders(){return registeredRiders;}
        public Account getAccountById(Long id){
            for(Account account: registeredAccounts)if(account.id.equals(id))return account;
            return null;
        }
        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addCustomer(Customer customer){
            registeredAccounts.add(customer);
            registeredCustomers.add(customer);
        }
        public Customer getCustomerById(Long id){
            for(Customer customer: registeredCustomers)if(customer.id.equals(id))return customer;
            return null;
        }
        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRestaurant(Restaurant restaurant) {
            registeredAccounts.add(restaurant);
            registeredRestaurants.add(restaurant);
        }
        public Restaurant getRestaurantById(Long id){
            for(Restaurant restaurant: registeredRestaurants)if(restaurant.id.equals(id))return restaurant;
            return null;
        }
        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRider(Rider rider){
            registeredAccounts.add(rider);
            registeredRiders.add(rider);
        }
        public Rider getRiderById(Long id){
            for(Rider rider: registeredRiders)if(rider.id.equals(id))return rider;
            return null;
        }
    }
    protected static AccountManager accountManager = new AccountManager();
    /// Task 2: Implement the register method.
    public abstract void register();
    public static Account getAccountById(Long id){return accountManager.getAccountById(id);}
    /// Do not modify this method.
    public static AccountManager getAccountManager(){return accountManager;}
}