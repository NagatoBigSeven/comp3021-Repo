package base;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
public class Location{
    Double latitude, altitude;
    Location(Double latitude, Double altitude){
        this.latitude = latitude;
        this.altitude = altitude;
    }
    @Override
    public String toString(){return "Location(latitude=" + latitude + ", altitude=" + altitude +")";}
    public Double distanceTo(Location other){return sqrt(pow((latitude - other.latitude), 2) + pow((altitude - other.altitude), 2));}
}
