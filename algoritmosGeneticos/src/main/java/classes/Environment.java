
package classes;
import java.util.ArrayList;

public class Environment {
    // Attributes
    private final int x_size, y_size;  // Limits of the image
    private final int amount_individuals;  // How many individuals we want per generation
    public ArrayList<Generation> generationsList = new ArrayList<Generation>();  
    
    
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
        for(int i = 0; i < amount_individuals; i++){
            // Adds n individuals
            gen1.addIndividual(createFistGenIndividual(), i);
        }
        generationsList.add(gen1);  
    }
    
    // Creates a new individual from 2 given parents
    private Individual createIndividual(Generation lastGeneration){
        // Creates a random position
        
        Individual father = lastGeneration.selectIndividual();
        Individual mother = lastGeneration.selectIndividual();
        
        // Creates and returns a new individual
        return new Individual(father, mother);
    }
    
    public void createGeneration(){
        Generation lastGeneration = generationsList.get(generationsList.size()-1);
        
        Generation newGen = new Generation(amount_individuals);  // Creates the generation container
        for(int i = 0; i < amount_individuals; i++){
            // Adds n individuals
            newGen.addIndividual(createIndividual(lastGeneration), i);
        }
        generationsList.add(newGen);  
    }
    
}
