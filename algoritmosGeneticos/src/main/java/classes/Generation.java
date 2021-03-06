
package classes;

public final class Generation {
    // Attibutes
    public final Individual[] individuals;  // Holds all the individuals produced in this generation
    private Individual[] selectionRoulette;  // Algoritmos Genéticos - Semana 11 - Page 3
    private final int amount_individuals;  // How many individuals this genration will have
    public float generation_average;  // Average score from 0 to 0.99 of all the individuals of this generation 
  
    // Constructor
    public Generation(int amountIndividuals){
        this.amount_individuals = amountIndividuals;
        individuals = new Individual[amountIndividuals];
        selectionRoulette = new Individual[100];
    }
    
    
    // Methods
    
    // Adds a new individual to individuals array, used when the generation is just being created
    public void addIndividual(Individual ind, int i){
        individuals[i] = ind;
    }
    
    
    /* Distributes the individuals occupation on the the roulette according to their score giving them higher or lower chance of being select to reproduce
    Remember selectionRoulette is an array of 100 spaces
    For example, we have 4 individuals
    The first individual got a normalized integer score of 10
    It will have position 0-9 on the selection roulette
    The second individual got a normalized integer score of 40
    It will have position 10-49 on the selection roulette
    The third individual got a normalized integer score of 20
    It will have position 50-69 on the selection roulette
    The last individual got a normalized integer score of 30
    It will have position 70-99 on the selection roulette
    Finally filling the 100 spaces according to the scores of each individual
    */
    private void fillGenerationRoulette(float sum){
        int limit;  // Helps to know from where to where an individul will occupy on the roulette
        int h = 0;
        
        // Goes individual by individual
        for (Individual individual : individuals){
                            // Gets the normalized score of the current individual
            limit = h+individual.setNormalizedScore(sum); 
            
            // Fills the spaces the current individual desreves on the roulette
            while (h < limit){
                selectionRoulette[h] = individual;
                if(h == 100){
                    break;
                }
                h++;
            }
            
            if(h == 100){
                    break;
                }
        }
    }
    
    // Gives a fitness score to all the individuals of the generation
    public void ApplyFitnessScore(){
        // Sets to all the individuals the real fitness score taking into account the neighbors data
        float sum = 0;  // Gets the sum of the fitness scores of all the individuals
        for (Individual individual : individuals){
            sum += individual.calculateFitnessScore(individuals);
        }

        generation_average = sum / amount_individuals; // Sets the average score of the generation
        fillGenerationRoulette(sum);
    }
    
    
    /*Selects an individuals of this generation to reproduce
    Randomly selects it from the selectionRoulette*/
    public Individual selectIndividual(){
        Individual ind = selectionRoulette[(int)Math.floor(Math.random()*100)];
        if (ind == null){
            ind = individuals[(int)Math.floor(Math.random()*amount_individuals)];
        }
        return ind;
    }
    
}
