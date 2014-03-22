package other;

public class Pixel 
{
    public byte r,g,b;
    public int x,y;
    public Pixel(byte r, byte g, byte b, int x, int y)
    {
        this.r=r;
        this.g=g;
        this.b=b;
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "Pixel{" + "r=" + (r&0xff) + ", g=" + (g&0xff) + ", b=" + (b&0xff) + ", x=" + x + ", y=" + y + '}';
    }
    
}
