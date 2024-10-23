package base;
public abstract class Icon {
    private char base;
    public Icon(char base){
        this.base = encircle(base);
    }
    public char getBase(){return base;}
    protected abstract char encircle(char c);
    @Override
    public String toString(){
        return "" + base;
    }
}
