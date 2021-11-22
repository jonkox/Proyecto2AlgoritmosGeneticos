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
        
        Image hola = new Image();
        
        hola.getImageInfo("src\\main\\java\\images\\mapa4.png");
        //hola.printMatrix();
        hola.editImage(2);
        
        Individual nuevo = new Individual(49,26);

        nuevo.calculateIncompleteScore(hola.matrix, 3);
        
        System.out.println(nuevo.incomplete_score);
        
        
        /*
        Individual Chepe = new Individual(21, 22);
        Individual Tona = new Individual(28, 29);
        
        Individual Cervando = new Individual(31, 32);
        Individual Custodia = new Individual(38, 39);
        
        Individual Jesus = new Individual(41, 42);
        Individual Chaya = new Individual(48, 49);
        
        
        Individual Orlando = new Individual(Pelico, EspPelica, 1);
        Individual Olga = new Individual(Chepe, Tona, 1);
        
        Individual Mateo = new Individual(Cervando, Custodia, 0);
        Individual Tita = new Individual(Jesus, Chaya, 0);
        
        
        Individual father = new Individual(Orlando, Olga, 0);
        Individual mother = new Individual(Mateo, Tita, 1);
        
        Individual me = new Individual(father, mother, 0);
        mother.getGeonolgy();
        
        for (int i = 0; i < 100; i++)
        System.out.println((int)Math.floor(Math.random()*(2)));*/
    }
}
