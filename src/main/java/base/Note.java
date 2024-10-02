package base;
import java.util.Date;
import java.util.Objects;
public class Note implements Comparable<Note>{
    private Date date;
    private String title;
    static long counter = 1L;
    public Note(String title){
        this.date = new Date(counter);
        this.title = title;
        ++counter;
    }
    public Date getDate(){return date;}
    public String getTitle(){return title;}
    @Override
    public String toString(){return date.toString() + "\t" + title;}
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
    @Override
    public int compareTo(Note other){return -date.compareTo(other.date);}
}
