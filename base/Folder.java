package base;
import java.util.ArrayList;
import java.util.Objects;

public class Folder{
    private ArrayList<Note> notes;
    private String name;
    public Folder(String name){
        notes = new ArrayList<Note>();
        this.name = name;
    }
    public void addNote(Note note){notes.add(note);}
    public String getName(){return name;}
    public ArrayList<Note> getNotes(){return notes;}
    @Override
    public String toString(){
        int numOfTextNotes = 0, numOfImageNotes = 0;
        for(Note note: notes){
            if(note instanceof TextNote)++numOfTextNotes;
            else if(note instanceof ImageNote)++numOfImageNotes;
            else;
        }
        return name + ':' + numOfTextNotes + ':' + numOfImageNotes;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(name, folder.name);
    }
}
