
package main;


import genetic.Chromosome;
import genetic.EnvironmentTest;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.Log;


public class Starter 
{
    public static void main(String[] args) 
    {
        try 
        {
            Log.isWindow(true);
            EnvironmentTest tc = new EnvironmentTest();            
            Chromosome ch=null;
            for (int i=0; i<10000; i++)// generations
            {
               // Log.println("Generation #"+i);            
                tc.nextGeneration();                
                if(ch!=tc.getCurrentBest())
                {
                    ch=tc.getCurrentBest();
                    Log.println("New best DNA: ["+Chromosome.stringFromDna(ch.getDna())+"]");
                }
            }
            Log.println("-------------");
            Log.println("Best fitness of "+tc.getHistoricalMaxFitness());
            Log.println("Average fitness of " +tc.getHistoricalAverageFitness());
            Log.println("String from best DNA: " +"["+tc.getStringFormOfBestHistoricalDna()+"]");
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
