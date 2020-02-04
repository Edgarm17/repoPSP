/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semafors;

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
public class FitxerLiniesPart2 {

    private static File arxiu = new File("linies.txt");
    private static File nouArxiu = new File("liniescanviades.txt");

    private static final Semaphore MUTEX = new Semaphore(1);
    private static final Semaphore PARELL_ESCRIT = new Semaphore(0);
    private static final Semaphore IMPARELL_ESCRIT = new Semaphore(1);

    public static void main(String[] args) throws IOException {

        proces1 p1 = new proces1();
        proces2 p2 = new proces2();

        p1.start();
        p2.start();

    }

    static class proces1 extends Thread {

        private String linea;
        private BufferedReader br;

        public proces1() {
        }

        public void run() {
            try {
                FileReader fr = new FileReader(arxiu);
                br = new BufferedReader(fr);
                linea = br.readLine();
                do {

                    PARELL_ESCRIT.acquire();
                    MUTEX.acquire();

                    if (linea != null) {
                        System.out.println("Linea: " + linea + " , Thread: " + " IMPARES ");
                        FileWriter fw = new FileWriter(nouArxiu, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.write(linea + "\n");
                        pw.close();
                    }

                    linea = br.readLine();
                    linea = br.readLine();

                    MUTEX.release();
                    IMPARELL_ESCRIT.release();
                } while (linea != null);
                br.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Arxiu no trobat");
            } catch (IOException ex) {
                System.out.println("final");
            } catch (InterruptedException ex) {
                Logger.getLogger(FitxerLiniesPart2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static class proces2 extends Thread {

        private String linea;
        private BufferedReader br;

        public proces2() {
        }

        public void run() {
            try {
                FileReader fr = new FileReader(arxiu);
                br = new BufferedReader(fr);
                do {

                    IMPARELL_ESCRIT.acquire();
                    MUTEX.acquire();

                    linea = br.readLine();
                    linea = br.readLine();
                    if (linea != null) {
                        System.out.println("Linea: " + linea + " , Thread: " + " PARES ");
                        FileWriter fw = new FileWriter(nouArxiu, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.write(linea + "\n");
                        pw.close();
                    }

                    MUTEX.release();
                    PARELL_ESCRIT.release();
                } while (linea != null);
                br.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Arxiu no trobat");
            } catch (IOException ex) {
                System.out.println("final");
            } catch (InterruptedException ex) {
                Logger.getLogger(FitxerLiniesPart2.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
