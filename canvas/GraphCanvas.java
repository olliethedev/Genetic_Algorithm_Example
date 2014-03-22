package canvas;

import java.util.ArrayList;
import other.Pair;
import other.Pixel;


/**
 *
 * @author Oleksiy
 */
public class GraphCanvas extends WindowsCanvas
{
    ArrayList<Pair<Integer,Integer>> data;
    ArrayList<Pixel> graphPixels;
    public GraphCanvas(int width, int height)
    {
        super(width,height);
        this.graphPixels=new ArrayList<>();
        this.data= new ArrayList<>();
    }
    public void addGraphData(ArrayList<Pair<Integer,Integer>> data,int r,int g,int b)
    {
        int maxX=0;
        int maxY=0;
        for(int i =0; i<data.size(); i++)
        {
            maxX=data.get(i).one>maxX?data.get(i).one:maxX;
            maxY=data.get(i).two>maxY?data.get(i).two:maxY;
        }
        //System.out.println("graph data maxX:"+maxX+" maxY:"+maxY);
        for(int i =0; i<data.size(); i++)
        {
            int x=(int)((float)this.windowWidth*(float)data.get(i).one/(float)maxX);
            int y=(int)((float)this.windowHeight-(float)this.windowHeight*(float)data.get(i).two/(float)maxY);
            //System.out.println("x,y : "+x+","+y);
            graphPixels.add(new Pixel((byte)r, (byte)g, (byte)b, x, y));
            graphPixels.add(new Pixel((byte)r, (byte)g, (byte)b, x+1, y));
            graphPixels.add(new Pixel((byte)r, (byte)g, (byte)b, x, y+1));
            graphPixels.add(new Pixel((byte)r, (byte)g, (byte)b, x+1, y+1));
        }
    }
    public void setGraphData(ArrayList<Pair<Integer,Integer>> data,int r,int g,int b)
    {
        int maxX=0;
        int maxY=0;
        for(int i =0; i<data.size(); i++)
        {
            maxX=data.get(i).one>maxX?data.get(i).one:maxX;
            maxY=data.get(i).two>maxY?data.get(i).two:maxY;
        }
        //System.out.println("graph data maxX:"+maxX+" maxY:"+maxY);
        ArrayList<Pixel> graphPixelsTemp = new ArrayList<>();
        for(int i =0; i<data.size(); i++)
        {
            int x=(int)((float)this.windowWidth*(float)data.get(i).one/(float)maxX);
            int y=(int)((float)this.windowHeight-(float)this.windowHeight*(float)data.get(i).two/(float)maxY);
            //System.out.println("x,y : "+x+","+y);
            graphPixelsTemp.add(new Pixel((byte)r, (byte)g, (byte)b, x, y));
            graphPixelsTemp.add(new Pixel((byte)r, (byte)g, (byte)b, x+1, y));
            graphPixelsTemp.add(new Pixel((byte)r, (byte)g, (byte)b, x, y+1));
            graphPixelsTemp.add(new Pixel((byte)r, (byte)g, (byte)b, x+1, y+1));
        }
        this.graphPixels=graphPixelsTemp;
    }
    public void drawGraph2D()
    {        
        this.render(graphPixels.toArray(new Pixel[graphPixels.size()]));
    }
}
