package classes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Jhonny Diaz
 */
public class Image {
    int matrix[][];
    public int row;
    public int column;
    
    
    /**
     * This method take an image and take all pixel's information, this creates a matrix where will be allocated the info
     * it only make sense with labyrinth's images because it will assign:
     * 0 for black or non-significant colors
     * 1 for white
     * 2 for finish
     * 3 for start
     * @param path path is the directory for the image that we want to take
     */
    public void getImageInfo(String path){
        
        
        try {
            
            //create the image and the buffer
            InputStream myImage = new FileInputStream(path); 
            ImageInputStream imageInput = ImageIO.createImageInputStream(myImage);
            BufferedImage bfImage = ImageIO.read(imageInput);
            
            //take basic info about the image
            row = bfImage.getHeight();
            column = bfImage.getWidth();
            matrix = new int[row][column];
            System.out.println("fila  " + row + "  columnas  " + column);
            
            
            //the cycle which goes for all pixels
            for(int i = 0; i<bfImage.getHeight();i++){
                for(int j = 0; j<bfImage.getWidth();j++){
                    
                    int srcPixel = bfImage.getRGB(i, j); //take the pixel
                    
                    Color c = new Color(srcPixel); //take the pixel's color
                    
                    //take every RGB color 
                    int R = c.getRed();
                    int G = c.getGreen();
                    int B = c.getBlue();
                    
                    //assign and write the code of the color
                    if (R == 0 && G == 0 && B == 0) {
                        matrix[i][j] = 0;
                    }
                    else if (R == 255 && G == 255 & B == 255) {
                        matrix[i][j] = 1;
                    }
                    else if (R == 255 && G == 0 & B == 0) {
                        matrix[i][j] = 2;
                    }
                    else if (R == 0 && G == 0 & B == 255) {
                        matrix[i][j] = 3;
                    }
                    else{
                        matrix[i][j] = 0;
                    }                   
                }
            }
            
            for(int i = 0; i<row;i++){
                for(int j = 0; j<column;j++){
                    System.out.print(matrix[i][j]+" ");
                }
                System.out.println("");
                
            }
            
        } catch (IOException e) {
            System.out.println("Me caí");
        }
    }
}
