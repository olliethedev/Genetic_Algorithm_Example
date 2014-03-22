package genetic;

import java.util.ArrayList;
import other.Log;

/**
 *
 * @author Oleksiy
 */
public class ChromosomePopulation 
{
    private ArrayList<Chromosome> population;
    private float populationFitness=0;
    private float populationMaxFitness=0;
    private float populationAvgFitness=0;
    private boolean hasMutations=false;
    private int geneSize;
    public ChromosomePopulation(int populationSize, int dnaLength, int geneSize,  IFitnessTester tester) throws Exception
    {
        this.geneSize=geneSize;
        population  = new ArrayList<>();
        populationSize=populationSize%2!=0?populationSize+=1:populationSize;//we need even number of organisms in the population
        Log.println("First generation DNA:");
        for(int i =0; i<populationSize; i++)
        {
            population.add(new Chromosome(dnaLength, geneSize, tester));
        }
        Log.println("---------");
    }
    public void nextGeneration() throws Exception
    {
        ArrayList<Chromosome> takenChroms = new ArrayList<>();
        
        ArrayList<Chromosome> newPopulation = new ArrayList<>();
        ArrayList<Chromosome> singleChroms = new ArrayList<>(this.population);
        population.removeAll(takenChroms);
        while(takenChroms.size()<=this.population.size())
        {
            Chromosome dad =this.getMostFit(singleChroms);
            takenChroms.add(dad);
            ArrayList<Chromosome>children = findPair(dad,takenChroms);
            for(Chromosome child : children)
            {
            
                if(child.isMutated())
                {
                    this.hasMutations=true;
                }
                newPopulation.add(child);
            }
        }
        
        this.population=newPopulation;
    }
    private Chromosome getMostFit(ArrayList<Chromosome> data)
    {
        Chromosome max = data.get(0);
        for(Chromosome c : data)
        {
            if(c.getFitness()>max.getFitness())
            {
                max=c;
            }
        }
        return max;
    }
    public Chromosome getMostFit()
    {
        return this.getMostFit(population);
    }
    public ArrayList<Chromosome> findPair(Chromosome dad,ArrayList<Chromosome> takenChroms) throws Exception
    {
        ArrayList<Chromosome> children = new ArrayList<>();
        ArrayList<Chromosome> populationCopy = new ArrayList<>(this.population);
        population.removeAll(takenChroms);
        ArrayList<Chromosome> roulette = new ArrayList<>();
        float totalFitness=getFitness();
        int count =0;
        
        for(int i =0; i<populationCopy.size();i++)
        {
            //System.out.println(""+i);

            int places =(int) (((float)populationCopy.get(i).getFitness()/(float)totalFitness)*100f);
            //System.out.println("places: "+places);
            for(int c=0; c<places; c++)
            {
                roulette.add(populationCopy.get(i));
                count++;
            }

        }
        if(count==0)
        {
            System.out.println("null1");
            return children;
        }
        boolean done =false;
        int randomIndex= (int) (Math.random()*((roulette.size()-0)));;
        
        Chromosome mom = roulette.get(randomIndex);
        children.add(dad.getOffspring(mom));
        children.add(mom.getOffspring(dad));
        takenChroms.add(mom);//dad is added from nextGeneration()
        //note if needed change the number of children
        
        return children;
        
        
    }
    public float getFitness()
    {
        float fitness =1;
        float maxFitness=0;
        for(Chromosome ch : population)
        {
            float tempFit =ch.getFitness();
            fitness+=tempFit;
            if(tempFit>maxFitness)
            {
                maxFitness=tempFit;
            }
        }
        this.populationFitness=fitness;        
        this.populationAvgFitness=fitness/population.size();
        this.populationMaxFitness=maxFitness;
        return this.populationFitness;
    }
    public boolean hasMutations()
    {
        return this.hasMutations;
    }
}
