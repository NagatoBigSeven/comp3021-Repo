package base;
public class IconLowerCase extends Icon{
    public IconLowerCase(char base){
        super(base);
    }
    @Override
    protected char encircle(char c){
        return (char)(c - 'a' + 'ⓐ');
    }
}
