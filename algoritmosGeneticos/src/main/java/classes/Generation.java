
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
    public void ApplyFitnessScore(){
        
    }
    
    
    //Selects 2 individuals of this generation to reproduce and together generate a new individual
    public Individual selectIndividual(){
        return new Individual(1, 1);
    }
    
}
