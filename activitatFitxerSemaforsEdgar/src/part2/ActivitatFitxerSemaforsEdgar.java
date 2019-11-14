/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part2;

import activitatfitxersemaforsedgar.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar
 */
public class ActivitatFitxerSemaforsEdgar {
    
    private static File arxiu = new File("linies.txt");
    
    private static final Semaphore MUTEX = new Semaphore(0);
    private static final Semaphore NUM_ESCRIT = new Semaphore(0);
//    private static final Semaphore MUTEX = new Semaphore(0);

    
    public static void main(String[] args) {
        proces1 p1 = new proces1();
        proces2 p2 = new proces2();
        
        p1.start();
        p2.start();
    }
    
    static class proces1 extends Thread{
        
        private File nouArxiu = new File("linescanviades.txt");
        private int cont = 1;
        public proces1(){
            
        }
        
        
        public void run(){
            try {
                
                System.out.println("Proces1");
                FileReader fr = new FileReader(arxiu);
                BufferedReader br = new BufferedReader(fr);
                
                FileWriter fw = new FileWriter(nouArxiu,true);
                PrintWriter pw = new PrintWriter(fw);
                
                String linea = br.readLine();
                while (linea!= null) {
                    
                    if (cont%2 != 0) {
                        pw.write(linea+"\n");
                    }
                    cont++;
                    linea = br.readLine();
                }
                
                br.close();
                pw.close();
                MUTEX.release();
                
            } catch (FileNotFoundException ex) {
                System.err.println("Arxiu no trobat");
            } catch (IOException ex) {
                System.out.println("final");
            }
        }
    }
    
    static class proces2 extends Thread{
        
        private File nouArxiu = new File("linescanviades.txt");
        private int cont = 1;
        
        public proces2(){
            
        }
        
        
        public void run(){
            try {
                MUTEX.acquire();
                System.out.println("Proces 2");
                FileReader fr = new FileReader(arxiu);
                BufferedReader br = new BufferedReader(fr);
                
                FileWriter fw = new FileWriter(nouArxiu,true);
                PrintWriter pw = new PrintWriter(fw);
                
                String linea = br.readLine();
                while (linea!= null) {
                    if (cont%2 == 0) {
                        pw.write(linea+"\n");
                    }
                    cont++;
                    linea = br.readLine();
                }
                
                br.close();
                pw.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Arxiu no trobat");
            } catch (IOException ex) {
                System.out.println("final");
            } catch (InterruptedException ex) {
                Logger.getLogger(ActivitatFitxerSemaforsEdgar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
