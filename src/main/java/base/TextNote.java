package base;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
public class TextNote extends Note implements Iconifiable{
    private String content;
    public TextNote(String title){super(title);}
    public TextNote(String title, String content){
        super(title);
        this.content = content;
    }
    public TextNote(File f){
        super(f.getName());
        this.content = getTextFromFile(f.getAbsolutePath());
    }
    public TextNote(TextNote other){
        super(other);
        this.content = other.content;
    }
    public String getContent(){return content;}
    @Override
    public String toString(){return "TextNote: " + super.toString() + "\t" + (content != null && content.contains(".") ? content.split("\\.")[0].length() >= 30 ? content.split("\\.")[0].substring(0, 30) : content.split("\\.")[0] : "");}
    public void iconify(){
        char firstCharacter = content.charAt(0);
        if('A' <= firstCharacter && firstCharacter <= 'Z'){
            content = new IconUpperCase(firstCharacter).getBase() + content.substring(1);
        }
        else if('a' <= firstCharacter && firstCharacter <= 'z'){
            content = new IconLowerCase(firstCharacter).getBase() + content.substring(1);
        }
        else if('0' <= firstCharacter && firstCharacter <= '9'){
            content = new IconDigit(firstCharacter).getBase() + content.substring(1);
        }
    }
    public String getTextFromFile(String absolutePath){
        StringBuilder result = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)))){
            String str;
            while((str = br.readLine()) != null){
                result.append(str).append("\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public void exportTextToFile(String pathFolder){
        File file = new File(pathFolder + File.separator + this.getTitle().replace(' ', '_') + ".txt");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write(this.content);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
