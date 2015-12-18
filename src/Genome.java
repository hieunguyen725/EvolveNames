/*
* Hieu Trung Nguyen
*/

import java.util.*;

/*
* This is a genome class which represent a genome string in a
* virtual world of containing only strings of characters. The genome
* is able to mutate, crossover with another genome and has its own
* fitness score.
*/
public class Genome implements Comparable<Genome> {
    private Random rand;
    public String genomeName; // left as public for testing in main
    private String targetName;
    private double mutationRate;
    private char[] charList;
    
    /*
    *  Construct a new Genome of value 'A' with the given mutation rate
    */
    public Genome(double mutationRate) {
        this.mutationRate = mutationRate;
        this.genomeName = "A";
        this.targetName = new Population().target;
        rand = new Random();
        charList = new char[29];
        // Thanks to ASCII Table
        for (int i = 0; i < 26; i++) {
            charList[i] = (char) (i + 65);
        }
        charList[26] = '_';
        charList[27] = '-';
        charList[28] = (char) 39;
    }
    
    /*
    * Construct a new Genome with the same value and
    * mutation rate with the given gene.
    */
    public Genome(Genome gene) {
        this(gene.mutationRate);
        this.genomeName = gene.genomeName;
    }
    
    /*
    * Mutate the genome by either add, delete or substitute the string with different
    * characters.
    */
    public void mutate() {
        // Thanks to previous CSE 142 website for a quick review of the Random object and substring
        double mutateChance = rand.nextDouble();
        // add
        if (mutateChance <= mutationRate) {
            char randomChar = charList[rand.nextInt(charList.length)];
            int randomPosition = rand.nextInt(genomeName.length() + 1);
            String partOne = genomeName.substring(0, randomPosition);
            String partTwo = genomeName.substring(randomPosition);
            genomeName = partOne + randomChar + partTwo;
        }
        // delete
        mutateChance = rand.nextDouble();
        if (genomeName.length() > 1 && mutateChance <= mutationRate) {
            int randomPosition = rand.nextInt(genomeName.length());
            String partOne = genomeName.substring(0, randomPosition);
            String partTwo = genomeName.substring(randomPosition + 1);
            genomeName = partOne + partTwo;
        }
        // substitute
        for (int i = 0; i < genomeName.length(); i++) {
            mutateChance = rand.nextDouble();
            if (mutateChance <= mutationRate) {
                char randomChar = charList[rand.nextInt(charList.length)];
                String partOne = genomeName.substring(0, i);
                String partTwo = genomeName.substring(i + 1);
                genomeName = partOne + randomChar + partTwo;
            }
        }
    }
    
    /*
    * Crossover by updating this current Genome with the other given genome.
    */
    public void crossover(Genome other) {
        String newName = "";
        int i = 0;
        boolean crossedAll = false;
        while (!crossedAll) {
            boolean currentGenome = rand.nextBoolean();
            if (currentGenome) {
                if (i < this.genomeName.length()) {
                    newName += this.genomeName.charAt(i);
                } else {
                    crossedAll = true;
                }
            } else {
                if (i < other.genomeName.length()) {
                    newName += other.genomeName.charAt(i);
                } else {
                    crossedAll = true;
                }
            }
            i++;
        }
        genomeName = newName;
    }
    
    /*
    * Returns the fitness score of this genome
    */
    public Integer fitness() {
        Integer targetLength = targetName.length();
        Integer currentLength = genomeName.length();
        Integer penalty = Math.abs(targetLength - currentLength);
        for (int i = 0; i < Math.max(targetLength, currentLength); i++) {
            if (i >= targetLength || i >= currentLength) {
                penalty++;
            } else if (genomeName.charAt(i) != targetName.charAt(i)) {
                penalty++;
            }
        }
        return penalty;
    }
    
    /*
    * Extra Credit Portion
    * Returns the fitness score of this genome using the Wagner-Fischer algorithm
    */
//    public Integer fitness() {
//        int n = genomeName.length(); // row
//        int m = targetName.length(); // column
//        int[][] arr = new int[n + 1][m + 1];
//        for (int i = 0; i < m + 1; i++) {
//            arr[0][i] = i;
//        }
//        for (int i = 0; i < n + 1; i++) {
//            arr[i][0] = i;
//        }
//        for (int i = 1; i < n + 1; i++) {
//            for (int j = 1; j < m + 1; j++) {
//                if (genomeName.charAt(i - 1) == targetName.charAt(j - 1)) {
//                    arr[i][j] = arr[i - 1][j - 1];
//                } else {
//                    arr[i][j] = Math.min(Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1), arr[i - 1][j - 1] + 1);
//                }
//            }
//        }
//        return arr[n][m] + (Math.abs(n - m) + 1) / 2;
//    }
    
    /*
    * Display the representation of this genome with its value and fitness score
    */
    public String toString() {
        return "(\"" + genomeName + "\", " + this.fitness() + ")";
    }
    
    /*
    * Returns the comparing value of this genome with the other base on
    * their fitness scores
    */
    public int compareTo(Genome other) {
        return this.fitness() - other.fitness();
    }
}

// http://www.asciitable.com/
// http://courses.cs.washington.edu/courses/cse142/13au/lectures/13-ch05-2-random.pdf
// http://courses.cs.washington.edu/courses/cse142/13au/lectures/11-ch04-2-strings.pdf