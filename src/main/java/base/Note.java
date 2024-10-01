package base;
import java.util.Date;
import java.util.Objects;
public class Note{
    private Date date;
    private String title;
    public Note(String title){
        this.date = new Date();
        this.title = title;
    }
    public Date getDate(){return date;}
    public String getTitle(){return title;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof Note)return title.equals(((Note)o).title);
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
