
package classes;

import java.util.ArrayList;

public final class Individual {
    // Attributes
    public int x, y;  // Position (Genes), cant be final (constant) due to mutations
    private final int generation;  // Which generation this individual belongs, first gen, second gen, third gen, ...
    private final int radio; // Vision rate used several times in the fitness score methods
    public float incomplete_score, final_score, normalized_score;  // Needed to calculate the fitness score
    private final Individual father, mother; // Parents
    private final int[][] matrix;  // Map (matrix) that contains the individual and its environment, used several times in the fitness score methods
    
    
    // Constructors
    
    // For first generation. Doesnt get parents, they are in null. Are created on a random position
    public Individual(int x, int y, int radio, int[][] matrix){
        this.matrix = matrix;
        this.radio = radio;
        this.x = x;
        this.y = y;
        father = mother = null;
        generation = 0; // First genration
        
        // incomplete_score
        calculateIncompleteScore();  // Sets an initial fitness score to this individual without taking into account the individuals
    }
    
    // For general generations. Gets parents as parameters.
    public Individual(Individual father, Individual mother, int mutationIndex){
        this.father = father;
        this.mother = mother;
        
        // Determines if which position is taken from the father and which is taken from the mother
        int order = (int)Math.floor(Math.random()*2);
        
        if(order == 0){
            // Horizontal from father and vertical from mother
            x = father.x;
            y = mother.y;
            
        } else {
            // Horizontal from mother and vertical from father
            x = mother.x;
            y = father.y;
        }
        
        int mutatationNumber = (int)Math.floor(Math.random()*100);
        if (mutatationNumber < mutationIndex){
            mutate();
        }
        
        generation = father.generation + 1;
        this.matrix = father.matrix;
        this.radio = father.radio;
        
        // incomplete_score
        calculateIncompleteScore();  // Sets an initial fitness score to this individual without taking into account the individuals
    }
    
    
    // Methods
    
    
    /*
    Produces a mutation
    A gen(position) randomly changes
    It can change the x position, y position or in rare cases both can mutate
    */
    public void mutate(){
        int x_limit = matrix[0].length;
        int y_limit = matrix.length;
        
        int option = (int)Math.floor(Math.random()*100);
        
        if(option < 45){
            // Has a 45% chance of mutating the x (gene) position
            x = (int)Math.floor(Math.random()*x_limit);
        } else if (option < 90){
            // Has another 45% chance of mutating the y (gene) position
            y = (int)Math.floor(Math.random()*y_limit);
        } else {
            // Finally has a 10% chance of mutating both, the x and y (genes) positions
            x = (int)Math.floor(Math.random()*x_limit);
            y = (int)Math.floor(Math.random()*y_limit);
        }
    }
    
    
    
    /*Checks the environment around it
    DOESNT check the neighborgs
    Checks if the pixels around are part of of the path, start, ending ot black
    To see if this position is part of a good area
    This adds a certain value to the individual*/
    private int lookAround(){
        int addedScore = 0;  // Value to return
        
        int auxX = x;
        int auxY = y;
    
        auxX = x - (radio+1); // Moves to start at the left of the individual
        auxY = y - (radio+1);  // Moves to start up from the individual

        // to avoid recalculating these in every check of the stop condition in every iteration
        int x_limit = x + radio;  // Stops at the right of the individual
        int y_limit = y + radio;  // Stops under the individual
        
        int currentPixel;

        for (int i = auxX; i < x_limit ; i++) {
            for (int j = auxY; j < y_limit; j++) {
                try {
                    currentPixel = matrix[i][j];
                    if (currentPixel == 0) {
                        // Is black, bad. We add 0 so we dont do anything
                        continue;
                    } else if (currentPixel == 1){
                        // Is white, very good
                        addedScore += 2;
                    }
                    
                    else {
                        // Is red, kind of good
                        addedScore++;
                    }
                    
                 } catch (IndexOutOfBoundsException exception) {
                     /* Got out of the matrix, we take as 0
                     We add 0, so we dont add anything*/
                    continue; 
                }

            }
        }
            
        return addedScore;
    }
    
  
    
    /* Calculates the fitness score without taking into account the neighbors score
    Saves the result in the variable incomplete_score
    */
    public void calculateIncompleteScore(){
        // Calculates an initial score according the pixels behind the individual
        switch (matrix[x][y]) {
            case 0:  // Black
                incomplete_score = 0;  // Base score is low, black is very bad
                break;
                
                
            case 1:  // White
                incomplete_score = 25;  // Base score is high, white is inside the path very good
                break;
                
                
            default:  // Is red(start or ending)
                incomplete_score = 15;  // Red is medium, we want to be near the start or end but not inside it
                break;
        }
          incomplete_score += lookAround();  // Adds values according to the surroundings
    }
    
    
    /*Checks the neighborgs
    Checks the scores of the neighborgs to see if it is part of the good individuals
    This adds a certain value to the individual*/
    private int lookForNeighborgs(Individual[] individuals){
        int addedScore = 0;  // Value to return
        
        int auxX = x;
        int auxY = y;
    
        auxX = x - (radio+1); // Moves to start at the left of the individual
        auxY = y - (radio+1);  // Moves to start up from the individual

        // to avoid recalculating these in every check of the stop condition in every iteration
        int x_limit = x + radio;  // Stops at the right of the individual
        int y_limit = y + radio;  // Stops under the individual
        
        int i_maxBound = matrix.length;
        int j_maxBound = matrix[0].length;
        
        int x_closeness;
        int y_closeness;
        for (int i = auxX; i < x_limit ; i++) {
            x_closeness = 1;
            for (int j = auxY; j < y_limit; j++) {
                y_closeness = 1;
                
                if(i < i_maxBound && j < j_maxBound) {
                    for (Individual individual : individuals){
                        if(i == individual.x && j == individual.y){
                                                                      // The closer the neighbor is to this individual, the higher its added score gets
                            addedScore += individual.incomplete_score*(y_closeness+x_closeness);
                            break;
                        }
                    }
                    
                 } else {
                     /* Got out of the matrix, we take as 0
                     We add 0, so we dont add anything*/
                    break; // We are already out, we pass to the next line
                }
                
                
                // Tells how close vertically the neighbor of the next iteration is to the current individual
                if(j > y){
                    // Is getting futher
                    y_closeness--;
                } else {
                    // Is getting closer
                    y_closeness++;
                }
                
            }
            
            // Tells how close vertically the neighbor of the next iteration is to the current individual
            if(i > x){
                // Is getting futher
                    x_closeness--;
                } else {
                    // Is getting closer
                    x_closeness++;
                }
        }
            
        return addedScore;
    }
    
    
    
    /* Calculates the fitness score taking into account the neighbors score
    The parameter visionRange tells how many pixels away an individual can search for neighbors
    It he individual can see at the most 3 pixels from itslef, or 5 pixels from itself...
    Saves the result of the fitness score in the final_score variable and returns that value
    */
    public float calculateFitnessScore(Individual[] individuals){
        final_score = incomplete_score + lookForNeighborgs(individuals);
        return final_score;
    }
    
    
    /* Normalizes the final score putting it between 0 and 1
    It calculates and average to normalized
    It returns an integer approximation between 0 and 100 of the normalized values
    That returned integer number between 0 and 100 is needed by the class Generation
    The normalized score is used for the selection roulette
    */
    public int setNormalizedScore(float sumResult){
        normalized_score = final_score / sumResult;
        return (int) normalized_score*100;
    }
    
    /*Prints spaces
    Used by the method printTree() to produce the desired format
    */
    private void printGap(double amount){
        for (int i = 0; i < amount; i++){
            System.out.print(" ");
        }
    }
    
    /*
    Visually prints a tree
    Example:
    Generacion 1:     x x x x x x x x
                      \ / \ / \ / \ /
    Generacion 2:      x   x   x   x
                        \  /   \   /
    Generacion 3:         x      x
                           \    /
    Generacion 4:            x
    
    Gets as parameter a list of lists (like a matrix)
    Each list are the individuals of each generation (the parameter is organized by generation)
    Prints the first generation and its rows, prints the second generation and its rows and so on
    */
    private void printTree(ArrayList<ArrayList<String>> geonology){
        int gap[] = {5, 20, 50, 110};  // Horizontal space between an inidividual and the next in the same generation (line) for the nice case
        int initial[] = {0, 8, 22, 48, 108};  // Starting spaces for each line in the nice case
        double generation_size;  // Needed for calculations of the nice case
        ArrayList<String> generation_list;  // Needed to print individual by individual in the nice case
        int index;  // Needed to adjust the nice case
        
        System.out.print("Generacion 1:   ");  // Starts the printing
        
        // Moves from one generation to the next
        for(int i = 0; i < generation; i++){
            if (generation - i > 4){
                // General case
                
                // Generation is to old and to big
                System.out.println(geonology.get(i) + "\n");  // just prints the list
                System.out.print("Generacion " + (i+2) + ":   ");  // for the next generation (iteration)
                
            } else {
                // Nice case, the generation is close enough to THIS individual to worth drawing the tree
                
                index = 4-generation+i;  // To work properly with the positions of the arrays gap[] and initial[]
                generation_list = geonology.get(index);  // Gets the individuals of the generation is going to be printed
                generation_size = generation_list.size();  // for stop condition
                

                // Moves among the inidviduals of this generation
                for(String individual_info : generation_list){
                   System.out.print(individual_info);  // Prints an individual
                   printGap(gap[index]);
                }

                // Prints the rows
                System.out.print("\n                ");  // Line jump to create a line for rows and spaces are needed for format
                generation_size /= 2;  // Resizing end condition to get the correct format
                printGap(initial[index]);

                // Moves under the inidviduals of this generation to print its row
                for(int j = 0; j < generation_size; j++){
                   // Row of father
                   System.out.print("     \\    ");
                   printGap(gap[index]);

                   // Row of father
                   System.out.print("     /    ");
                   printGap(gap[index]);
                }


                // Prepares for the next generation (iteration)
                System.out.println("");  // Creates new line
                System.out.print("Generacion " + (i+2) + ":   ");
                printGap(initial[index+1]);
            }
            
        }
        
        System.out.println(geonology.get(generation).get(0));  // Finishes printing THIS individual
    }
    
    /*
    This method shows the geonology of the individual
    Father and Mother - Grandparents by the father - Grandparents by the mother - Greatgrandparents and so on
    Works recursively, based on a binary tree
    Returns a list of the geonology organized by generations
    */
    private ArrayList<ArrayList<String>> getGeonolgyAux(Individual ind, ArrayList<ArrayList<String>> generations_list){ 
        if(ind != null){  // Stop condition
            // General case
            generations_list = getGeonolgyAux(ind.father, generations_list);  // Recursive call to father
            String info = "x: " + ind.x + ", y: " + ind.y;
            generations_list.get(ind.generation).add(info);  // Puts itself in the corresponding generation
            generations_list = getGeonolgyAux(ind.mother, generations_list);  // Recursive call to mother    
        }
        
        return generations_list;
    }
    
    /* Prints the geneology of this individual
    Calls the recursive method with the correct starting value to get the geonological tree as a list
    Then calls a method that prints that list*/
    public void getGeonolgy(){

        // Creates parameter for the recursive function
        ArrayList<ArrayList<String>> geonology = new ArrayList<ArrayList<String>>();
        for (int i = 0; i <= generation; i++){
            geonology.add(new ArrayList<String>());
        }
        
                  // Recursive function that returns geonology organized by generations
        printTree(getGeonolgyAux(this, geonology));  // Visually prints the tree
    }
    
    // Calculates the fitness score without taking into account the neighbors score
    /*public void calculateIncompleteScore(){
        if(isOnWhite()){
            // At least it is on a white position, there it will get a score over 0, good or bad
            
            // Checks if it is on dead end
            if(isOnDeadEnd()){
                incomplete_score = 1;
            }
                   
        }
        
        // The else will be it is in black, is left with the default 0
        
    }*/
    
    // Calculates the fitness score taking into account the neighbors score
    public void calculateFitnessScore(Individual _individual, int[][] matrix){}

}
