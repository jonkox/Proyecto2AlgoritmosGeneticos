
package classes;

import java.util.ArrayList;

public class Individual {
    // Attributes
    public int x, y;  // Position (Genes), cant be final (constant) due to mutations
    private final int generation;  // Which generation this individual belongs, first gen, second gen, third gen, ...
    public float incomplete_score, final_score, normalized_score;  // Needed to calculate the fitness score
    private final Individual father, mother; // Parents
    
    
    // Constructors
    
    // For first generation. Doesnt get parents, they are in null. Are created on a random position
    public Individual(int x, int y){
        this.x = x;
        this.y = y;
        this.father = null;
        this.mother = null;
        generation = 0; // First generation
        this.incomplete_score = 0;
        this.final_score = 0;
        
    }
    
    // For general generations. Gets parents as parameters.
    public Individual(Individual father, Individual mother){
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
        
        generation = father.generation + 1;
        this.incomplete_score = 0;
        this.final_score = 0;
    }
    
    
    // Methods
    
    
    /*
    Produces a mutation
    A gen(position) randomly changes
    It can change the x position, y position or in rare cases both can mutate
    */
    public void mutate(int x_limit, int y_limit){
        int option = (int)Math.floor(Math.random()*100);
        
        if(option < 45){
            // Has a 45% chance of mutating the x position
            x = (int)Math.floor(Math.random()*x_limit+1);
        } else if (option < 90){
            // Has another 45% chance of mutating the y position
            y = (int)Math.floor(Math.random()*y_limit+1);
        } else {
            // Finally has a 10% chance of mutating both, the x and y positions
            x = (int)Math.floor(Math.random()*x_limit+1);
            y = (int)Math.floor(Math.random()*y_limit+1);
        }
    }
    
  
    
    /* Calculates the fitness score without taking into account the neighbors score
    Saves the result in the variable incomplete_score
    */
    public void calculateIncompleteScore(int matrix[][], int radio){
        
        switch (matrix[x][y]) {
            case 0:
                
                if (lookAround(radio, matrix, 1)) {
                    incomplete_score = 10;
                }
                else{
                    incomplete_score = 0;
                }
               
                break;
            case 1:
                if (lookAround(radio, matrix, 0)) {
                    incomplete_score = 25;
                }
                else{
                    incomplete_score = 30;
                }
                break;
            case 2:
                if (lookAround(radio, matrix, 2)) {
                    incomplete_score = 50;
                }
                break;
            case 3:
                if (lookAround(radio, matrix, 3)) {
                    incomplete_score = 15;
                }
                else{
                    incomplete_score = 20;
                }
                break;

            default:
                break;
        }
        
        // The else will be it is in black, is left with the default 0
        
    }
    
    /* Calculates the fitness score taking into account the neighbors score
    The parameter visionRange tells how many pixels away an individual can search for neighbors
    It he individual can see at the most 3 pixels from itslef, or 5 pixels from itself...
    Saves the result of the fitness score in the final_score variable and returns that value
    */
    public float calculateFitnessScore(int visionRange){
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

    public boolean lookAround(int radio, int matrix[][], int watchFor){
        int auxX = x;
        int auxY = y;

        
        try {
            x = x - (radio+1);
            y = y - (radio+1);
            
            for (int i = this.x; i < auxX + radio; i++) {
                for (int j = this.y; j < auxY + radio; j++) {
                    System.out.print(matrix[i][j]);
                    if (matrix[i][j] == watchFor) {
                        return true;
                    }
                    
                }
                System.out.println("");
            }
            
            
        } catch (IndexOutOfBoundsException exception) {
            if (x < 0) {
                x = 0;
                System.out.println("x es menos que 0");
            }
            else if (y < 0) {
                y = 0;
                System.out.println("y es menos de 0");
            }
            else if (y + radio > matrix.length || x + radio > matrix[x].length) {
                System.out.println("se sale del limite");
                for (int i = this.x; i < matrix.length; i++) {
                    for (int j = this.y; j < matrix[i].length; j++) {
                        /*
                        if (matrix[i][j] == watchFor) {
                            return true;
                        }*/
                    }
                }
            }
            
            /*
            for (int i = this.x; i < auxX + radio; i++) {
                for (int j = this.y; j < auxY + radio; j++) {
                    System.out.print(matrix[i][j]);
                    
                    if (matrix[i][j] == watchFor) {
                        return true;
                    }

                }
                System.out.println("");
            }*/
        }
           
        
        this.x = auxX;
        this.y = auxY;
        
        return false;
        
    
    }
    


}
