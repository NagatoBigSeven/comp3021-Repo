package base;
import java.io.File;
public class ImageNote extends Note {
    private Icon icon;
    private File image;
    public ImageNote(String title){
        super(title);
        char firstCharacter = title.charAt(0);
        if('A' <= firstCharacter && firstCharacter <= 'Z'){
            this.icon = new IconUpperCase(firstCharacter);
        }
        else if('a' <= firstCharacter && firstCharacter <= 'z'){
            this.icon = new IconLowerCase(firstCharacter);
        }
        else if('0' <= firstCharacter && firstCharacter <= '9'){
            this.icon = new IconDigit(firstCharacter);
        }
    }
    public ImageNote(ImageNote other){
        super(other);
        this.icon = other.icon;
        this.image = other.image;
    }
    @Override
    public String toString(){return "ImageNote: " + icon + " " + super.toString();}
}