package genetic;

import java.util.ArrayList;
import other.Log;

/**
 *
 * @author Oleksiy
 */
public class Chromosome 
{
    private byte[] dna;
    private static final float MUTATION_RATE=0.03f;//todo: increase mutation when fitness score is low, decrese mutation when fitness score is high 
    private IFitnessTester tester;
    private boolean hasMutation=false;
    private int geneSize;
    public int id =-1;//used for debugging only
    public Chromosome(int dnaLength, int geneSize , IFitnessTester tester) throws Exception//constructor for first gen
    {
        if(dnaLength%geneSize!=0)
        {
            throw new Exception("dna legth must be devisible by gene size");
        }
        this.tester=tester;
        this.geneSize=geneSize;
        byte[] randomData=new byte[dnaLength];
        
        for(int i=0; i<randomData.length; i++)
        {
            double r =Math.random();
            double r2 =Math.random();
            if(r<=0.5)
            {
                randomData[i]=(byte)(Byte.MIN_VALUE*r2);
            }
            else
            {
                randomData[i]=(byte)(Byte.MAX_VALUE*r2);
            }
        }
        Log.println("["+stringFromDna(randomData)+"]");
        this.dna=randomData;
    }
    public Chromosome(byte[]dna, int geneSize ,  IFitnessTester tester, boolean hasMutation) throws Exception//constructor for 2nd to n'th gen
    {
        if(dna.length%geneSize!=0)
        {
            throw new Exception("dna legth must be devisible by gene size");
        }
        this.dna=dna;
        this.geneSize=geneSize;
        this.tester=tester;
        this.hasMutation = hasMutation;
    }

    public Chromosome getOffspring(Chromosome other) throws Exception
    {
        if(this.getDna().length!=other.getDna().length)
        {
            throw new Exception("cant cross two dna with different lengths");
        }
        float usFitness = this.getFitness();
        float themFitness=other.getFitness();        
        float fitnessDiff = usFitness-themFitness;
        byte[] newDna=new byte[this.getDna().length>other.getDna().length?this.getDna().length:other.getDna().length];
        float denum=(usFitness>themFitness?usFitness:themFitness);        
        float percentDnaUs=0.5f+0.5f*(fitnessDiff==denum?0:fitnessDiff/denum);       
        float percentDnaThem =1-percentDnaUs;
        int geneCount = this.dna.length/this.geneSize;
        
        ArrayList<Integer> statusList = new ArrayList<>();
        boolean mutation =false;
        boolean done=false;
        
        while(!done)//fill part of dna from this parent
        {
            int randomIndex =(int) (Math.random()*geneCount);
            if(!statusList.contains(randomIndex))//if unfilled item exists
            {
                for(int i =0; i<geneSize; i++)
                {                  
                    int newIndex = randomIndex*geneSize+i;
                    newDna[newIndex]=this.dna[newIndex];
                }
                
                statusList.add(randomIndex);
            }
            if(((float)statusList.size()/geneCount)>=percentDnaUs)
            {
                done=true;
            } 
        }  

        done=false;
        while(!done)//fill part of dna from other parent
        {
            int randomIndex =(int) (Math.random()*geneCount);
            if(!statusList.contains(randomIndex))//if unfilled item exists
            {
                for(int i =0; i<geneSize; i++)
                {                    
                    int newIndex = randomIndex*geneSize+i;
                    newDna[newIndex]=other.getDna()[newIndex];
                }
                statusList.add(randomIndex);
            }
            if(statusList.size()>=geneCount)//if finished filling dna data
            {
                done=true;
            } 
        }
                
        for(int i =0; i<newDna.length; i++)
        {
            float mutationPercentage = Chromosome.MUTATION_RATE*100f;
            float randomChance = (float) (Math.random()*100f);
            
            if(randomChance< mutationPercentage)
            {
                //System.out.println("MUTATION!!!");
                mutation=true;
                double r =Math.random();
                double r2 =Math.random();
                if(r<=0.5)
                {
                    newDna[i]=(byte)(Byte.MIN_VALUE*r2);
                }
                else
                {
                    newDna[i]=(byte)(Byte.MAX_VALUE*r2);
                }
                
            }
        }
        
        return new Chromosome(newDna,geneSize, tester, mutation);//todo mutate gene length
    }
    public float getFitness()
    {
         return this.tester.getScoreFromTest(this.getDna());
    }
    public static String stringFromDna(byte[] dna)
    {
        String str="";
        for(int i =0; i<dna.length; i++)
        {
             str+=(char)(dna[i]+Byte.MAX_VALUE);            
        }
        return str;
    }
    public boolean isMutated()
    {
        return this.hasMutation;
    }
    public byte[] getDna()
    {
        return this.dna;
    }
            
}
