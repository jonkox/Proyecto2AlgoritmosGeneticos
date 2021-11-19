
package classes;
import java.util.ArrayList;

public class Environment {
    // Attributes
    private final int x_size, y_size;  // Limits of the image
    private final int amount_individuals;  // How many individuals we want per generation
    public ArrayList<Generation> generationsList = new ArrayList<Generation>();  // Holds all the generations that had been created
    
    
    // Constructor
    public Environment(int amount_individuals, int x_size, int y_size){
        this.amount_individuals = amount_individuals;
        this.x_size = x_size;
        this.y_size = y_size;
    }
    
    
    // Methods

    // Creates a new individual on a random coordenate without parents
    private Individual createFistGenIndividual(){
        // Creates a random position
        int x = (int)Math.floor(Math.random()*(x_size+1));
        int y = (int)Math.floor(Math.random()*(y_size+1));
        
        // Creates and returns a new individual
        return new Individual(x, y);
    }
    
    // Creates n new individuals randomly to start the program
    public void makeFistGeneration(){
        Generation gen1 = new Generation(amount_individuals);  // Creates the generation container
        
        // Adds n individuals
        for(int i = 0; i < amount_individuals; i++){
            // Adds a new individual
            gen1.addIndividual(createFistGenIndividual(), i);
        }
        generationsList.add(gen1);  // We store this generation we just have created in our list
    }
    
    // Creates a new individual from 2 given parents
    private Individual createIndividual(Generation lastGeneration){
        // Randomly selects 2 individuals to reproduce together
        Individual father = lastGeneration.selectIndividual();
        Individual mother = lastGeneration.selectIndividual();
        
        // Creates a new individual (children) based on the parents and returns it
        return new Individual(father, mother);
    }
    
    
    
    // Creates a new generation (of children) based on the preexisting parents
    public void createNewGeneration(){
        // Gets the last generation, these will be the parents of the new gneration
        Generation lastGeneration = generationsList.get(generationsList.size()-1);
                                      /* This number is how many pixels of range an individual has to search for neighbors
                                         |
                                         V  */
        lastGeneration.ApplyFitnessScore(5);  // Tells the parents generation to give all the individuals a fitness score to be ready for the selection
        
        // Creates the generation container for the new ones
        Generation newGeneration = new Generation(amount_individuals);  
        
        // Creates n new individuals (children)
        for(int i = 0; i < amount_individuals; i++){
            // Adds a new individual (children)
            newGeneration.addIndividual(createIndividual(lastGeneration), i);
        }
        generationsList.add(newGeneration);  // We store this generation we have just created in our list
    }
    
}
