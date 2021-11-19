/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proofs;
import classes.Image;

import classes.Individual;


/**
 *
 * @author Jhonny Diaz
 */
public class main {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Image hola = new Image(); 
        hola.getImageInfo("src\\main\\java\\images\\mapa2.png");*/
        
        
        Individual Adan = new Individual(12, 18);
        Individual Eva = new Individual(17, 15);
                
        
        Individual Pelico = new Individual(Adan, Eva);
        Individual EspPelica = new Individual(Adan, Eva);
        
        Individual Chepe = new Individual(Adan, Eva);
        Individual Tona = new Individual(Adan, Eva);
        
        Individual Cervando = new Individual(Adan, Eva);
        Individual Custodia = new Individual(Adan, Eva);
        
        Individual Jesus = new Individual(Adan, Eva);
        Individual Chaya = new Individual(Adan, Eva);
        
        
        Individual Orlando = new Individual(Pelico, EspPelica);
        Individual Olga = new Individual(Chepe, Tona);
        
        Individual Mateo = new Individual(Cervando, Custodia);
        Individual Tita = new Individual(Jesus, Chaya);
        
        
        Individual father = new Individual(Orlando, Olga);
        Individual mother = new Individual(Mateo, Tita);
        
        Individual me = new Individual(father, mother);
        me.getGeonolgy();
        

    }
}
