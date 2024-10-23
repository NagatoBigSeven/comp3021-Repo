package base;
import java.io.Serial;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.io.Serializable;
public class Folder implements Comparable<Folder>, Serializable, Cloneable{
    private ArrayList<Note> notes;
    private String name;
    @Serial
    private final static long serialVersionUID = 1L;
    public Folder(String name){
        notes = new ArrayList<>();
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
            else{
                if(note instanceof ImageNote){
                    ++numOfImageNotes;
                }
            }
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
    @Override
    public int compareTo(Folder other){return name.compareTo(other.name);}
    @Override
    protected Folder clone(){
        try{
            Folder clone = (Folder)super.clone();
            clone.notes = new ArrayList<>();
            for(Note note: notes){
                if(note instanceof TextNote textnote){
                    clone.notes.add(new TextNote(textnote));
                }
                else if(note instanceof ImageNote imageNote){
                    clone.notes.add(new ImageNote(imageNote));
                }
                else{
                    clone.notes.add(new Note(note));
                }
            }
            return clone;
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }
    public void sortNotes(){Collections.sort(notes);}
    private boolean contains(Note note, String keyword){
        if (note.getTitle().toUpperCase().contains(keyword))return true;
        else if(note instanceof TextNote){
            if(((TextNote) note).getContent() == null)return false;
            return ((TextNote) note).getContent().toUpperCase().contains(keyword);
        }
        else return false;
    }
    public List<Note> searchNotes(String keywords){
        List<Note> searchedNotes = new ArrayList<>();
        String[] splitKeywords = keywords.toUpperCase().split(" ");
        for(Note note: notes){
            boolean isSearchedNote = true;
            boolean[] isChecked = new boolean[splitKeywords.length];
            int k = 0;
            while(k < splitKeywords.length){
                if(splitKeywords[k].equals("OR")){
                    boolean flg = contains(note, splitKeywords[k - 1]);
                    isChecked[k - 1] = isChecked[k] = true;
                    while(k + 2 < splitKeywords.length && splitKeywords[k + 2].equals("OR")){
                        k += 2;
                        if(contains(note, splitKeywords[k - 1]))flg = true;
                        isChecked[k - 1] = isChecked[k] = true;
                    }
                    if(contains(note, splitKeywords[k + 1]))flg = true;
                    isChecked[k + 1] = true;
                    if(!flg){
                        isSearchedNote = false;
                        break;
                    }
                }
                ++k;
            }
            if(!isSearchedNote)continue;
            for(int i = 0; i < splitKeywords.length; ++i){
                if(!isChecked[i]){
                    if(contains(note, splitKeywords[i])){
                        isSearchedNote = false;
                        break;
                    }
                }
            }
            if(isSearchedNote)searchedNotes.add(note);
        }
        return searchedNotes;
    }
}
