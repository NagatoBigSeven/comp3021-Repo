package hk.ust.comp3021;

public class Customer extends Account {
    private Integer customerType;
    private String gender;
    private String email;
    public Customer(Long id, String accountType, String name, String contactNumber, Location location, Integer customerType, String gender, String email){
        super(id, accountType, name, contactNumber, location);
        this.customerType = customerType;
        this.gender = gender;
        this.email = email;
    }
    public Integer getCustomerType(){return customerType;}
    /// Do not modify this method.
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", customerType=" + customerType +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    @Override
    public void register(){accountManager.addCustomer(this);}
    static Customer getCustomerById(Long id){return accountManager.getCustomerById(id);}
}
