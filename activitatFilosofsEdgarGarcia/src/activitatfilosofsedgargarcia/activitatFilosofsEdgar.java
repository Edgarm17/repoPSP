/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activitatfilosofsedgargarcia;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Edgar
 */
public class activitatFilosofsEdgar {

    private final static int NUM_FILOSOFS = 5;

    private final static int[][] FILOSOF_PALETS = {{0, 4}, {1, 0}, {2, 1}, {3, 2}, {4, 3}};

    private final static Semaphore[] PALETS_SEMAFORS = new Semaphore[NUM_FILOSOFS];

    public static void iniciarSemafors() {
        for (int i = 0; i < NUM_FILOSOFS; i++) {
            PALETS_SEMAFORS[i] = new Semaphore(1);
        }
    }

    public static void main(String[] args) {
        iniciarSemafors();

        for (int i = 0; i < NUM_FILOSOFS; i++) {
            new Filosof(i, PALETS_SEMAFORS, FILOSOF_PALETS).start();
        }
    }

    static class Filosof extends Thread {

        private int id;
        private int[][] filosofPalets;
        private Semaphore[] paletsSemafors;
        private int paletEsquerre;
        private int paletDret;

        public Filosof(int nFilosof, Semaphore[] semafor, int[][] filosofPalets) {
            this.id = nFilosof;
            this.paletsSemafors = semafor;
            this.filosofPalets = filosofPalets;
            this.paletEsquerre = filosofPalets[nFilosof][0];
            this.paletDret = filosofPalets[nFilosof][1];
        }

        public void menjar() {
            try {
                paletsSemafors[paletEsquerre].acquire();
                paletsSemafors[paletDret].acquire();
                System.out.println("El filòsof " + this.id + " está menjant.");
                sleep(new Random().nextInt(2000) + 1000);
                System.out.println("El filòsof " + this.id + " ha acabat de menjar y solta els palets: L[" + paletEsquerre + "], R[" + paletDret + "]");
                paletsSemafors[paletDret].release();
                paletsSemafors[paletEsquerre].release();
                System.out.println("El filòsof " + this.id + " està esperant.");
            } catch (Exception e) {
                System.out.println("Error en menjar: "+e.toString());
            }
        }
        
        public void pensar(){
            try {
                Filosof.sleep(new Random().nextInt(2000)+1000);
            } catch (Exception e) {
                System.out.println("Error en pensar: "+e.toString());
            }
        }

        @Override
        public void run() {
            pensar();
            menjar();
        }
        
        
    }
}
