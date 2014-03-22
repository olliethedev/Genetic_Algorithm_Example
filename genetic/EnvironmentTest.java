package genetic;

import canvas.GraphCanvas;
import java.util.ArrayList;
import other.ByteConverter;
import other.Pair;

/**
 *
 * @author Oleksiy
 */
public class EnvironmentTest implements IFitnessTester
{
    private ChromosomePopulation cp;
    public ArrayList<Pair<Integer,Integer>> data= new ArrayList<>();//arraylist for graph data
    public ArrayList<Pair<Integer,Integer>> mutationPoint = new ArrayList<>();
    private int time, geneSize;
    private float historicMaxFitness, historicalAverageFitness , historicalTotalFitness;
    private byte[] historicalBestDna;
    private GraphCanvas graphWindow= new GraphCanvas(800,600);
    private Chromosome currentBest;
    public EnvironmentTest() throws Exception
    {
        this.time=0;//generation count
        this.geneSize=1;//Character.SIZE/8;
        this.cp = new ChromosomePopulation(10, 7*geneSize, geneSize, this);//initial population number, dna data length, gene size
        this.historicMaxFitness =this.cp.getFitness();
        this.historicalTotalFitness+=this.historicMaxFitness;
        this.data.add(new Pair<>(this.time,(int)this.historicMaxFitness));//add initial data
        time++;
    }
    public void nextGeneration() throws Exception
    {   
        float tempFitness=this.cp.getFitness();

        if(tempFitness>this.historicMaxFitness)
        {
            this.historicMaxFitness=tempFitness;
            this.currentBest=this.cp.getMostFit();
            this.historicalBestDna=this.currentBest.getDna();            
        }

        this.historicalTotalFitness+=tempFitness;
        this.historicalAverageFitness =this.historicalTotalFitness/time;
        this.data.add(new Pair<>(this.time,(int)tempFitness));
        this.cp.nextGeneration();
        if(this.cp.hasMutations())
        {            
            mutationPoint.add(new Pair<>(this.time,(int)tempFitness));           
        }
        this.time++;
        graphWindow.setGraphData(data, 0 , 0, 0);
        graphWindow.drawGraph2D();
    }
    public byte[] getHistoricalBestDna()
    {
        return this.historicalBestDna;
    }
    public Chromosome getCurrentBest()
    {
        return this.currentBest;
    }
    public String getStringFormOfBestHistoricalDna()
    {
        String dnaStr =Chromosome.stringFromDna(this.historicalBestDna);
        return dnaStr;
    }
    public float getHistoricalMaxFitness()
    {
        return this.historicMaxFitness;
    }
    public float getHistoricalAverageFitness()
    {
        return this.historicalAverageFitness;
    }
    public float getHistoricalTotalFitness()
    {
        return this.historicalTotalFitness;
    }
    
    @Override
    public float getScoreFromTest(byte[] dna) 
    {
        float score =1;
        
        String str="";
        char[] testPoints={'o','l','e','k','s','i','y'};
        for(int i =0; i<dna.length; i++)
        {
             str+=(char)(dna[i]+Byte.MAX_VALUE);            
        }
        for(int i =0; i<testPoints.length; i++)
        {
            if(str.contains(String.valueOf(testPoints[i])))
            {
                score+=1+(str.indexOf(testPoints[i])==i?10:0);
            }

        }
       
        return score;
    }
    
}
