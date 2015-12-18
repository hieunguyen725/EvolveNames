/*
* Hieu Trung Nguyen
*/

import java.util.*;

/*
* This is a population class of genomes, it is able to
* update the genomes in the a population every breeding cycle.
*/
public class Population {
    public String target = "HIEU_TRUNG_NGUYEN";
    public Genome mostFit;
    private int numGenomes;
    private Random rand;
    private List<Genome> myPopulation;
    
    /*
    * Construct a population with the given number of genomes
    * and mutation rate.
    */
    public Population(Integer numGenomes, Double mutationRate) {
        myPopulation = new ArrayList<Genome>();
        for (int i = 0; i < numGenomes; i++) {
            myPopulation.add(new Genome(mutationRate));
        }
        mostFit = myPopulation.get(0);
        rand = new Random();
        this.numGenomes = numGenomes;
    }
    
    // This is my empty constructor since it is not part of the required interface.
    // An empty constructor with its purpose to pass the target string into the genome
    // class without doing all the work as the above constructor
    protected Population() {}
    
    /*
    * Carry out the breeding cycle by deleting the least-fit half of the
    * population, creating the other half by cloning a remaining genome, then
    * mutate or crossover and mutate. Finally, update the most fit genome
    * in the population.
    */
    public void day() {
        // delete
        for (int i = numGenomes - 1; i >= numGenomes / 2; i--) {
            myPopulation.remove(i);
        }
        // create
        boolean crossover;
        for (int i = 0; i < numGenomes / 2; i++) {
            Genome newGenome = new Genome(myPopulation.get(rand.nextInt(numGenomes / 2)));
            crossover = rand.nextBoolean();
            if (crossover) {
                newGenome.crossover(myPopulation.get(rand.nextInt(numGenomes / 2)));
                newGenome.mutate();
            } else {
                newGenome.mutate();
            }
            myPopulation.add(newGenome);
        }
        // update
        Collections.sort(myPopulation);
        // Thanks to Java API Library for this sort method
        mostFit = myPopulation.get(0);
    }
}

// http://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List)