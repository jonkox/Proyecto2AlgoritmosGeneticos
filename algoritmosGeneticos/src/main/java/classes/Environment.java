
package classes;
import java.util.ArrayList;

public final class Environment {
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

    /* Creates a new individual on a random coordenate without parents
    Auxiliary method for makeFirstGeneration()*/
    private Individual createFistGenIndividual(){
        // Creates a random position
        int x = (int)Math.floor(Math.random()*(x_size+1));
        int y = (int)Math.floor(Math.random()*(y_size+1));
        
        // Creates and returns a new individual
        return new Individual(x, y);
    }
    
    // Creates n new individuals randomly to start the program
    private void makeFirstGeneration(){
        Generation gen1 = new Generation(amount_individuals);  // Creates the generation container
        
        // Adds n individuals
        for(int i = 0; i < amount_individuals; i++){
            // Adds a new individual
            gen1.addIndividual(createFistGenIndividual(), i);
        }
        
        /* Gives all the individuals of the generation the fitness score to have it ready for the next generation
        This number is how many pixels of range an individual has to search for neighbors
                               |
                               V  */
        gen1.ApplyFitnessScore(5);  // Tells the parents generation to give all the individuals a fitness score to be ready for the selection
        
        generationsList.add(gen1);  // We store this generation we just have created in our list
    }
    
    /* Creates a new individual from 2 given parents
    Auxiliary method for createNewGeneration()*/
    private Individual createIndividual(Generation lastGeneration){
        // Randomly selects 2 individuals to reproduce together
        Individual father = lastGeneration.selectIndividual();
        Individual mother = lastGeneration.selectIndividual();
        
        // Creates a new individual (children) based on the parents and returns it
        return new Individual(father, mother);
    }
    
    
    
    // Creates a new generation (of children) based on the preexisting parents
    private void createNewGeneration(){
        // Gets the last generation, these will be the parents of the new gneration
        Generation lastGeneration = generationsList.get(generationsList.size()-1);

                                      /* This number is how many pixels of range an individual has to search for neighbors
                                         |
                                         V  */
        //lastGeneration.ApplyFitnessScore(5);  // Tells the parents generation to give all the individuals a fitness score to be ready for the selection
        

        // Creates the generation container for the new ones
        Generation newGeneration = new Generation(amount_individuals);  
        
        // Creates n new individuals (children)
        for(int i = 0; i < amount_individuals; i++){
            // Adds a new individual (children)
            newGeneration.addIndividual(createIndividual(lastGeneration), i);
        }
        
        
        /* Gives all the individuals of the generation we have just created the fitness score to have it ready for the next generation
        This number is how many pixels of range an individual has to search for neighbors
                                        |
                                        V  */
        newGeneration.ApplyFitnessScore(5);  // Tells the parents generation to give all the individuals a fitness score to be ready for the selection
        
        generationsList.add(newGeneration);  // We store this generation we have just created in our list
    }
    
    // INIT methods to start the simulation
    
    /*It start the simulation, starts the main program
    It creates and creates generations until a minimum score is reached
    In a bad case the simulation can be restarted if the program is producing worse generations
    The programs ends when a simulation satisfactorily reaches the minimum score defined by the user
    It receives as parameter the minimum score a generation has to reach
    It receives as parameter the max amount of wrong generations the user will tolerate before restarting the simulation
    */
    public void initMinimum(float min_score, int max_wrong){
        boolean hasToRestart;  // Flag variable
        
        do{ // A do-while because we need to run it at least once and its a loop because we may need to restart the simulation
            
            hasToRestart = false;  // At the start everything is fine
            
            makeFirstGeneration();  // Starts creating the first generation of random individuals

            int last_generation = 0;  // Index of the last generation
            int wrongGenerations = 0; // Amounts of generations that were worse than the last one
            
            float penultimate_score, last_score;  // Needed for comparisons
            last_score = generationsList.get(last_generation).generation_average;  

            // Starts creating generations until we reach the minimum score
            while(last_score < min_score){
                // The last generation created didnt get a score good enough
                createNewGeneration();  // we create a new generation to see if this new one is good enough
                last_generation++;
                
                // We update now who is the last and who is the penultimate
                penultimate_score = last_score;
                last_score = generationsList.get(last_generation).generation_average;
                
                // Check if the generation improved
                if(last_score < penultimate_score){
                    // The last generation had a lower score than its parents
                    wrongGenerations++;
                    
                    if(wrongGenerations > max_wrong){
                        /* We got too many bad generations
                        The program is not working properly because instead of improving its getting worse
                        We end this simulation and restart it to try again*/
                        generationsList.clear();  // Leave clean for the new simulation
                        hasToRestart = true;  
                        break;  // Forces the stop of the current simulation, the while(i <= generationsToCheck && stillCanImprove)
                    }
                }
            }
        } while(hasToRestart);
        
        /* Could get out of the while, therefore we got a generation that fullfilled our requirements. 
        We finish the program here*/
    }
    
    
    
    
    /*Verfies if the last n generations have changed a lot or not
    It receives the parameter generationsToCheck that tells the amount of generations we have to compare 
    It receives the parameter percentage_difference that tells how much the score of a generation can change at must compared to the others
    It compares a generation with the last one (we expect in this parameter a number under 1 but close to it, like 0.94 or 0.88)
    If the similarity found is over percentage_difference it returns false because we the last generations had almost the same score
    If the similarity found is under percentage_difference it returns true beacuse the last generation had a way higher score
    Auxiliary method for initCheckingLast()*/
    private boolean areDifferent(float percentage_difference, float penultimate_generation_score, float last_generation_score){
        /* Makes a division to compare both generations
        As it is supposed that the current generation is better than the previos one, 
        the penultimate should be just a fraction of the last one
        So the result of the divsion should be a number between 0 and 1
        
        In case there was a big improvement and the last generation is very different compared to the last one
        (last generation got a way higher score than the previous one)
        the result of the division should be closer to 0, look the following examples  15/45=0.3   12/51=0.23  24/83=0.28
        return true
        
        In case there was just a small improvement and both generations are very similar
        (last generation got a score just slightly higher than the penultimate)
        the results of the division sould be closer to 1, look the following examples: 49/50=0.98   87/100=0.87  23/25=0.92
        return false*/
        float similarity = penultimate_generation_score / last_generation_score;
        return similarity < percentage_difference;
    }
    
    
    
    /*It start the simulation, starts the main program
    It creates and creates generations until the generations are to good they are not improving anymore
    In a bad case the simulation can be restarted if the program is producing worse generations
    The programs ends when a simulation satisfactorily has produced generations good enough the improvement is minimal
    It receives as parameter the percental similarity among the last generations we want to reach to stop the simulation
    It receives as parameter the minimum amount of generations we want to create
    this last parameter also determines the maximun amount of wrong generations we will tolerate before restarting the simulation
    */
    public void initCheckingLast(float percentage_similarity, int generationsToCheck){
        boolean hasToRestart;  // Flag variable
        
        do {  // A do-while because we need to run it at least once and its a loop because we may need to restart the simulation
            
            hasToRestart = false;  // At the very start everything is okay
                
            makeFirstGeneration(); // Starts creating the first generation of random individuals
            int i = 0;  // Index of the last generation
            int wrongGenerations = 0;  // Tells how many generations where a failure
            
            boolean areDifferent = true;  // Used for the stop condtion
            float last_generation_score, penultimate_generation_score;  // Used for the comparisons

            // Gets the score of the very first generation, the only one we have created at this point
            last_generation_score = generationsList.get(i).generation_average;       
            
            /* Simulation - Creates n generations 
            Stops when where already created the amount of generations we wanted to check
            and the generations practically are not different
            In bad cases can stop and restart due to the bad results*/
            while(i <= generationsToCheck && areDifferent){
                createNewGeneration();  // we create a new generation to continue improving the individuals
                i++;  // Update the index

                // Updates which are the last 2 generations
                penultimate_generation_score = last_generation_score;
                last_generation_score = generationsList.get(i).generation_average;

                // Checks if the last generation actually is better than the last one
                if(last_generation_score < penultimate_generation_score){
                    // Something bad happened, the last generation instead of being better endend being worse
                    wrongGenerations++;

                    if(wrongGenerations > generationsToCheck){
                        /* We got too many bad generations
                        The program is not working properly because instead of improving its getting worse
                        We end this simulation and restart it to try again*/
                        generationsList.clear();  // Leave clean for the new simulation
                        hasToRestart = true;  
                        break;  // Forces the stop of the current simulation, the while(i <= generationsToCheck && stillCanImprove)
                    }

                } else {
                    /* The last generation was better than the penultimate
                    we check how good was that improvement*/
                    areDifferent = areDifferent(percentage_similarity, penultimate_generation_score, last_generation_score);
                }

            }

            /* Could get out of the while satisfactorily, consequently the change was not important
            and we already created the amount of generations we wanted
            and there was no point of continue to produce genrations that won´t improve anymore
            Also the simulation was not abruplty forced to stop by the break of bad results
            Therefore, we always got good results
            We finish the whole program here*/
        } while(hasToRestart);
        
    }
    
}
