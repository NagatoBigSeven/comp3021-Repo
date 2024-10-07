package base;
public class TextNote extends Note{
    private String content;
    public TextNote(String title){super(title);}
    public TextNote(String title, String content){
        super(title);
        this.content = content;
    }
    public String getContent(){return content;}
    @Override
    public String toString(){return "TextNote: " + super.toString() + "\t" + (content != null && content.contains(".") ? content.split("\\.")[0].length() >= 29 ? content.split("\\.")[0].substring(0, 29) + "." : content.split("\\.")[0] + "." : "");}
}
