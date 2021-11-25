/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proofs;
import classes.Environment;
import classes.Image;

import classes.Individual;
import java.rmi.activation.ActivationGroupID;


/**
 *
 * @author Jhonny Diaz
 */
public final class main {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Image im = new Image();
        im.getImageInfo("src\\main\\java\\images\\mapa5.png");
        
        Environment hola = new Environment(8, im.matrix, 3, 5);
        
        hola.makeFirstGeneration();
        
        for (int i = 0; i < 500; i++) {
            int t = (int)Math.floor(Math.random()*7);
            System.out.println(t);
            if (t >= 7) {
                
              System.out.println("acaaaaaaaaaa");  
            }
             
        }
        

    }
}
