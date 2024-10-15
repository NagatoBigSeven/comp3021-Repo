package base;
import java.io.Serial;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
public class NoteBook implements Serializable{
    private ArrayList<Folder> folders;
    @Serial
    private final static long serialVersionUID = 1;
    public NoteBook(){folders = new ArrayList<>();}
    public NoteBook(String file){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            NoteBook n = (NoteBook) in.readObject();
            this.folders = n.folders;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean createTextNote(String folderName, String title){
        TextNote note = new TextNote(title);
        return insertNote(folderName, note);
    }
    public boolean createTextNote(String folderName, String title, String content){
        TextNote note = new TextNote(title, content);
        return insertNote(folderName, note);
    }
    public boolean createImageNote(String folderName, String title){
        ImageNote note = new ImageNote(title);
        return insertNote(folderName, note);
    }
    public boolean insertNote(String folderName, Note note){
        Folder record = null;
        for(Folder folder: folders){
            if(folder.getName().equals(folderName)) {
                record = folder;
                break;
            }
        }
        if(record == null){
            record = new Folder(folderName);
            folders.add(record);
        }
        for(Note n: record.getNotes()){
            if(note.equals(n)){
                System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed.");
                return false;
            }
        }
        record.addNote(note);
        return true;
    }
    public ArrayList<Folder> getFolders(){return folders;}
    public void sortFolders(){
        for(Folder folder: folders)folder.sortNotes();
        Collections.sort(folders);
    }
    public List<Note> searchNotes(String keywords){
        List<Note> noteBookSearchedNotes = new ArrayList<>();
        for(Folder folder: folders){
            List<Note> folderSearchedNotes = folder.searchNotes(keywords);
            noteBookSearchedNotes.addAll(folderSearchedNotes);
        }
        return noteBookSearchedNotes;
    }
    public boolean createNote(String folderName, String title){return createImageNote(folderName, title);}
    public boolean createNote(String folderName, String title, String content){return createTextNote(folderName, title, content);}
    public boolean save(String file){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(this);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
