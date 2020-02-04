/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitatsRepas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Edgar
 */
public class Activitat1 {
    
    private static File arxiu = new File("text.txt");
    
    private static String[] linies;
    private static BufferedReader br;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(arxiu);
        String linea;
        br = new BufferedReader(fr);
        int cont = 1;
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        
        while ((linea = br.readLine()) != null) {
                    
            executor.execute(new FilRunnable(linea,cont));
            cont ++;
        }
        executor.shutdown();
        System.out.println("Acabat");
        
        br.close();
    }
    
    
    
    
    static class FilRunnable implements Runnable{

        private String linea;
        private int cont;
        private File nouArxiu = new File("textinvertit.txt");
        
        
        public FilRunnable(String linea,int cont) {
            this.linea = linea;
            this.cont = cont;
                   
        }
        
        

        @Override
        public void run() {
            FileWriter fw = null;
            try {
                fw = new FileWriter(nouArxiu,true);
                PrintWriter pw = new PrintWriter(fw);
                
                pw.write("Linea "+cont+": "+new StringBuilder(linea).reverse().toString()+"\n");
                
                pw.close();
            } catch (IOException ex) {
                System.out.println("Error");
            } 
        }
        
    }
}
