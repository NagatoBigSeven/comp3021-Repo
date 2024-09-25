package base;
import java.util.ArrayList;
public class NoteBook{
    private ArrayList<Folder> folders;
    public NoteBook(){folders = new ArrayList<Folder>();}
    public boolean createTextNote(String folderName, String title){
        TextNote note = new TextNote(title);
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
}
