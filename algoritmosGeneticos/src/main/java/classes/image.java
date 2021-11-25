package classes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Jhonny Diaz
 */
public final class Image {
    public int matrix[][];
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
            
            
            //the cycle which goes for all pixels
            for(int i = 0; i<row;i++){
                for(int j = 0; j<column;j++){
                    
                    
                    int srcPixel = bfImage.getRGB(i, j); //take the pixel
                    
                    Color c = new Color(srcPixel); //take the pixel's color
                    
                    //take every RGB color 
                    int R = c.getRed();
                    int G = c.getGreen();
                    int B = c.getBlue();
                    
                    

                    //assign and write the code of the color
                    if (R == 0 && G == 0 && B == 0) {
                        matrix[j][i] = 0;
                    }
                    else if (R == 255 && G == 255 & B == 255) {
                        matrix[j][i] = 1;
                    }
                    else if (R == 255 && G == 0 & B == 0) {
                        matrix[j][i] = 2;
                    }
                    else if (R == 0 && G == 0 & B == 255) {
                        matrix[j][i] = 2;
                    }
                    else{
                        matrix[j][i] = 0;
                    }   
                }
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    public void editImage(int generation){
        //create variables
        File image = null;
        BufferedImage bfImage = null;
        
        try {
            //instance variables
            image = new File("src\\main\\java\\images\\output1.png");
            bfImage = new BufferedImage(row,column,BufferedImage.TYPE_INT_ARGB);
            bfImage = ImageIO.read(image);

            
        } catch (IOException e) {
            
            System.out.println(e);
        }
        
        
        try {
            //In this section we want to use the main matrix to get every pixel's info
            //and edit the new image for every generation
            
            //Reset the image
            for(int i = 0; i<matrix.length;i++){
                
               for(int j = 0; j<matrix[i].length;j++){
                   
                    int rgb = new Color(255, 255, 255).getRGB();
                    bfImage.setRGB(i, j, rgb);
               }
            }

            //This cycle read the matrix and assign every pixel into the new image
            for(int i = 0; i<matrix.length;i++){
               for(int j = 0; j<matrix[i].length;j++){
                   switch (matrix[i][j]) {
                       case 0:
                           {
                               int rgb = new Color(0, 0, 0).getRGB();
                               bfImage.setRGB(i, j, rgb);
                               break;
                           }
                       case 1:
                           {
                               int rgb = new Color(100, 0, 100).getRGB();
                               bfImage.setRGB(i, j, rgb);
                               break;
                           }
                       case 2:
                           {
                               int rgb = new Color(255, 0, 0).getRGB();
                               bfImage.setRGB(i, j, rgb);
                               break;
                           }
                       case 3:
                           {
                               int rgb = new Color(0, 0, 255).getRGB();
                               bfImage.setRGB(i, j, rgb);
                               break;
                           }
                       default:
                           break;
                   }
               }
            }
  
            image = new File("src\\main\\java\\images\\output"+generation+".png");
            ImageIO.write(bfImage, "png", image);
            
        } catch (IOException e) {
            System.out.println("Me caí");
            System.out.println(e);
        }
    
    
    }
    
    public void printMatrix(){
        
        for(int i = 0; i<matrix.length;i++){
            for(int j = 0; j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");

        }
    }
    
}
