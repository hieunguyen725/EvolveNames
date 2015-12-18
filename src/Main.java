/*
* Hieu Trung Nguyen
*/

/*
* Main method for the population simulation.
*/
public class Main {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        Population population = new Population(100, 0.05);
        int generations = 0;
        System.out.println(population.mostFit);
        while (population.mostFit.fitness() != 0) {
            population.day();
            System.out.println(population.mostFit);
            generations++;
        }
        System.out.println("Generation: " + generations);
        long endTime = System.currentTimeMillis();
        long runTime = endTime - beginTime;
        System.out.println("Running time: " + runTime + " milliseconds");
        
        // Please comment out the above codes to test the methods below,
        // I think the while loop above is a good test for the population
        // class so I did not write test methods for it.
        
//        testCopyConstructor();
//        testMutate();
//        testCrossover();
    }
    
    public static void testCopyConstructor() {
        Genome g1 = new Genome(0.05);
        g1.genomeName = "WEEEE";
        Genome g2 = new Genome(g1);
        System.out.println("First genome = " + g1);
        System.out.println("Copy genome = " + g2);
    }
    
    public static void testMutate() {
        Genome g1 = new Genome(0.05);
        for (int i = 0; i < 200; i++) {
            g1.mutate();
            System.out.println(g1);
        }
    }
    
    public static void testCrossover() {
        Genome g1 = new Genome(0.05);
        g1.genomeName = "HIEU";
        Genome g2 = new Genome(0.05);
        g2.genomeName = "NGUYEN";
        System.out.println("First Genome = " + g1);
        System.out.println("Second Genome = " + g2);
        for (int i = 1; i <= 10; i++) {
            g1.crossover(g2);
            System.out.println("Cross try # " + i + " : " + g1);
            g1.genomeName = "HIEU";
        }
    }
}
