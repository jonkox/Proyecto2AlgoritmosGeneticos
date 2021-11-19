
package classes;

import java.util.ArrayList;

public class Individual {
    // Attributes
    public final int x, y;  // Position
    private final int generation;
    public float incomplete_score, final_score; 
    private final Individual father, mother; // Parents
    
    
    // Constructors
    
    // For first generation. Doesnt get parents, they are in null.
    public Individual(int x, int y){
        this.x = x;
        this.y = y;
        this.father = null;
        this.mother = null;
        generation = 0;
    }
    
    // For general generations. Gets parents as parameters.
    public Individual(Individual father, Individual mother, int order){
        this.father = father;
        this.mother = mother;
        
        // Determines if which position is taken from the father and which is taken from the mother
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
    }
    
    
    // Methods
    
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
        int gap[] = {5, 20, 50, 110};
        int initial[] = {0, 8, 22, 48, 108};  // Horizontal space between an inidividual and the next in the same generation (line)
        double generation_size;
        ArrayList<String> generation_list;
        int index;
        
        System.out.print("Generacion 1:   ");  // Starts the printing
        
        // Moves from one generation to the next
        for(int i = 0; i < generation; i++){
            if (generation - i > 4){
                System.out.println(geonology.get(i) + "\n");
                System.out.print("Generacion " + (i+2) + ":   ");
                
            } else {
                index = 4-generation+i;
                generation_size = Math.pow(2, generation-index);
                generation_list = geonology.get(index);

                // Moves among the inidviduals of this generation
                for(int j = 0; j < generation_size; j++){
                   System.out.print(generation_list.get(j));
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
    public void calculateFitnessScore(){
        
    }
}
