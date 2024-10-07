package base;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class NoteBook{
    private ArrayList<Folder> folders;
    public NoteBook(){folders = new ArrayList<>();}
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
}
