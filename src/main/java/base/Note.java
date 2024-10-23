package base;
import java.io.Serial;
import java.util.Date;
import java.util.Objects;
import java.io.Serializable;
public class Note implements Comparable<Note>, Serializable{
    private Date date;
    private String title;
    static long counter = 1L;
    @Serial
    private final static long serialVersionUID = 1L;
    public Note(String title){
        this.date = new Date(counter);
        this.title = title;
        ++counter;
    }
    public Note(Note other){
        this.date = other.date;
        this.title = other.title;
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
    public int compareTo(Note other){
        if(this instanceof TextNote && other instanceof ImageNote)return -1;
        if(this instanceof ImageNote && other instanceof TextNote)return 1;
        return title.compareTo(other.title);
    }
}