/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activitatsumavalorsedgar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Edgar
 */
public class ActivitatSumaValorsEdgar {

    private static File arxiu = new File("dades.txt");
    private static Semaphore MUTEX = new Semaphore(0);
    private static Semaphore NUEVO_ACOMULADOR = new Semaphore(0);
    private static Semaphore ACOMULADOR_ESCRITO = new Semaphore(1);
    private static Semaphore DADES_PLENES = new Semaphore(0);
    private static ArrayList<Integer> dades = new ArrayList<>();
    private static int acumulador = 0;
    private static boolean acabado = false;

    public static void main(String[] args) {
        Lector l = new Lector();
        Sumador s = new Sumador();
        Impressor i = new Impressor();
        
        l.start();
        s.start();
        i.start();
        
    }

    public static class Lector extends Thread {

        public Lector() {

        }

        public void run() {

            try {

                FileReader fr = new FileReader(arxiu);
                BufferedReader br = new BufferedReader(fr);

                String linea = br.readLine();
                while (linea != null) {
                    int valor = Integer.parseInt(linea);
                    dades.add(valor);
                    linea = br.readLine();
                }
                br.close();
                DADES_PLENES.release();
                MUTEX.release();
                

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    public static class Sumador extends Thread {

        public Sumador() {

        }

        public void run() {

            try {
                DADES_PLENES.acquire(); //semàfor que espera a que el arraylist de dades estiga ple
                for (int i = 0; i < dades.size(); i += 5) {
                    //semàfor que es pose en verd quan s'apunten els acumuladors en el nou fitxer
                    
                    ACOMULADOR_ESCRITO.acquire();
                    MUTEX.acquire();
                    for (int j = i; j < i + 5; j++) {
                        acumulador += dades.get(j);
                    }
                    MUTEX.release();
                    NUEVO_ACOMULADOR.release();
                }
                acabado = true;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }

    public static class Impressor extends Thread {

        private File resultat = new File("resultat.txt");

        public Impressor() {

        }

        public void run() {

            try {
                FileWriter fw = new FileWriter(resultat);
                PrintWriter pr = new PrintWriter(fw);
                while (!acabado) {
                    NUEVO_ACOMULADOR.acquire(); //semàfor que espera a que la nova suma de 5 en 5 estiga al acomulador
                    MUTEX.acquire();
                    //espera semafor
                    pr.write(acumulador + "\n");
                    MUTEX.release();
                    ACOMULADOR_ESCRITO.release();
                }
                
                pr.close();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } 

        }

    }

}
