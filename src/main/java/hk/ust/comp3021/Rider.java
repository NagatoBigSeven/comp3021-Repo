package hk.ust.comp3021;

public class Rider extends Account {
    private String gender;
    private Integer status;
    private Double userRating;
    private Integer monthTaskCount;
    public Rider(Long id, String accountType, String name, String contactNumber, Location location, String gender, Integer status, Double userRating, Integer monthTaskCount){
        super(id, accountType, name, contactNumber, location);
        this.gender = gender;
        this.status = status;
        this.userRating = userRating;
        this.monthTaskCount = monthTaskCount;
    }
    public String getGender(){return gender;}
    public Integer getStatus(){return status;}
    public void setStatus(Integer status){this.status = status;}
    public Double getUserRating(){return userRating;}
    public Integer getMonthTaskCount(){return monthTaskCount;}
    /// Do not modify this method.
    @Override
    public String toString() {
        return "Rider{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", userRating=" + userRating +
                ", monthTaskCount=" + monthTaskCount +
                '}';
    }
    @Override
    public void register(){accountManager.addRider(this);}
    public static Rider getRiderById(Long id){return accountManager.getRiderById(id);}
}
