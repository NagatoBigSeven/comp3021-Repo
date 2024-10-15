package base;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class TextNote extends Note{
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
    public String getContent(){return content;}
    @Override
    public String toString(){return "TextNote: " + super.toString() + "\t" + (content != null && content.contains(".") ? content.split("\\.")[0].length() >= 29 ? content.split("\\.")[0].substring(0, 29) + "." : content.split("\\.")[0] + "." : "");}
    public String getTextFromFile(String absolutePath){
        String result = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)))){
            String str;
            while((str = br.readLine()) != null){
                result += str + "\n";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
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
