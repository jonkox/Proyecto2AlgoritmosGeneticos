
package classes;
import classes.Individual;

public class Generation {
    // Attibutes
    private Individual[] individuals;  // Holds all the individuals produced in this generation
    
    // Constructor
    public Generation(int amountIndividuals){
        individuals = new Individual[amountIndividuals];
    }
    
    // Methods
    public void addIndividual(Individual ind, int i){
        individuals[i] = ind;
    }
    
    // Gives a fitness score to all the individuals of the generation
    public void ApplyFitnessScore(int range){
        // Sets to all the individuals a protype fitness score without taking into account the neighbors
        for (Individual individual : individuals){
            individual.calculateIncompleteScore();
        }
        
        // Sets to all the individuals the real fitness score taking into account the neighbors data
        for (Individual individual : individuals){
            individual.calculateFitnessScore(range);
        }
    }
    
    
    //Selects 2 individuals of this generation to reproduce and together generate a new individual
    public Individual selectIndividual(){
        return new Individual(1, 1);
    }
    
}
