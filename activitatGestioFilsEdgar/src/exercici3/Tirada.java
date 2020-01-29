/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar
 */
public class Tirada implements Runnable{
    
    private String nomFil;
    private int numTirada;
    private Recollidor recollidor;

    public Tirada(String nomFil, Recollidor recollidor) {
        this.nomFil = nomFil;
        this.recollidor = recollidor;
    }
    
    

    @Override
    public void run() {
        numTirada = new Random().nextInt(6) + 1;
        System.out.println(nomFil+" resultat de la tirada: "+numTirada);
        recollidor.mostrarSuma(numTirada);
        
    }
    
    public static void main(String[] args) {
        
        Recollidor r = new Recollidor();
        
        Tirada tirada1 = new Tirada("Tirada 1",r);
        Tirada tirada2 = new Tirada("Tirada 2",r);
        Tirada tirada3 = new Tirada("Tirada 3",r);
        
        Thread primer = new Thread(tirada1);
        Thread segon = new Thread(tirada2);
        Thread tercer = new Thread(tirada3);
        
        primer.start();
        segon.start();
        tercer.start();
        
        try {
            primer.join();
            segon.join();
            tercer.join();
        } catch (Exception e) {
            System.out.println("Error en el fil!");
        }
        
        System.out.println("Fi del programa principal!");
        
    }
    
    
    
}
