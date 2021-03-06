/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import classes.Individual;
import java.awt.Color;

/**
 *
 * @author andre
 */
public class RecreateGui extends javax.swing.JFrame {

    /**
     * Creates new form RecreateGui
     */
    
    private javax.swing.JButton[][] btnPixels;
    public RecreateGui() {
        initComponents();
    }
    
    public RecreateGui(int matrix[][], Individual[] individuals) {
        createButtons(matrix.length, matrix[0].length);
        printMap(matrix);
        initComponents();
        draw_individuals(individuals);  
        this.getContentPane().setBackground(new Color(64,64,150));
    }
    
    private void draw_individuals(Individual[] individuals){
        for(Individual individual : individuals){
            btnPixels[individual.x][individual.y].setBackground(new java.awt.Color(0, 255, 0));
        }
    }
    
    private void printMap(int matrix[][]){
        
        
        for (int i = 0; i < btnPixels.length; i++) {
            
            for (int j = 0; j < btnPixels[i].length; j++) {
                if (matrix[i][j] == 0) {
                    btnPixels[i][j].setBackground(Color.BLACK);
                }
                if (matrix[i][j] == 1) {
                    btnPixels[i][j].setBackground(Color.white);
                }
                if (matrix[i][j] == 2) {
                    btnPixels[i][j].setBackground(Color.red);
                }
                
                
            }
        }
    }
    
    public void createButtons(int x, int y) {
        btnPixels = new javax.swing.JButton[x][y];
        
        int space = 5;
        

        int vertical = 45;
        int horizontal;

        for (int i = 0; i < btnPixels.length; i++) {
            horizontal = 50;
            for (int j = 0; j < btnPixels[i].length; j++) {
                btnPixels[i][j] = new javax.swing.JButton();
                btnPixels[i][j].setBounds(horizontal, vertical, 7, 7);
                btnPixels[i][j].setBackground(new java.awt.Color(30, 30, 30));
                //btnPixels[i][j].
                add(btnPixels[i][j]);
                horizontal += space;
            }
            vertical += space;

        }         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Listo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Listo.setText("Listo");
        Listo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(322, Short.MAX_VALUE)
                .addComponent(Listo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(Listo)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListoActionPerformed
        this.dispose();
    }//GEN-LAST:event_ListoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RecreateGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RecreateGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RecreateGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RecreateGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecreateGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Listo;
    // End of variables declaration//GEN-END:variables
}
