/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activitataparcamentedgar;

import java.util.Random;

/**
 *
 * @author Edgar
 */
public class ActivitatAparcamentEdgar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitor m = new Monitor(40);
        int cont = 0;
        
        while (true) {
            Thread cotxe = new Cotxe("Coche "+cont,m);
            cotxe.start();
            cont++;
        }
        
    }
    
    static class Monitor{
        
        private int places;

        public Monitor(int places) {
            this.places = places;
        }
        
        public synchronized void aparcar(String nomCotxe){
            
            try {
                while (places <=0) {
                    System.out.println("No queden places, el coche espera");
                    wait();
                    
                }
                System.out.print(nomCotxe+" aparca en la plaça "+places);
                places--;
                System.out.println("            |Disponibles: "+places);
                Thread.sleep(new Random().nextInt(3000));
                notify();
                
                
            } catch (Exception e) {
                System.out.println("Error en aparcament");
            }
            
            
        }
        
        public synchronized void deixarAparcament(String nomCotxe){
            try {
                while (places >40) {
                    wait();
                    
                }
                System.out.print(nomCotxe+" deixa la plaça "+places);
                places++;
                System.out.println("            | Disponibles: "+places);
            } catch (Exception e) {
                System.out.println("Error al deixar plaça.");
            }
            
            
        }
    }
    
    
    static class Cotxe extends Thread{

        
        private String nom;
        private Monitor m;

        public Cotxe(String nom, Monitor m) {
            this.nom = nom;
            this.m = m;
        }
        
        @Override
        public void run() {
            m.aparcar(nom);
            m.deixarAparcament(nom);
        }
        
        
    }
    
}
