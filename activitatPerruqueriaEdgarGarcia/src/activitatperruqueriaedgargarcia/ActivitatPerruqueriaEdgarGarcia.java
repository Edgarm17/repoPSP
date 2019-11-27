/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activitatperruqueriaedgargarcia;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Edgar
 */
public class ActivitatPerruqueriaEdgarGarcia {

    private static int cadires = 5;
    
    private static Semaphore CLIENTS = new Semaphore(0);
    private static Semaphore PERRUQUER = new Semaphore(0);
    private static Semaphore MUTEX = new Semaphore(1);
    
    private static int clientsEsperant = 0;
    
    private static Client c[] = new Client[cadires];
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    static class Perruquer extends Thread{

        @Override
        public void run() {
            while (true) {
                try {
                    CLIENTS.acquire();
                    MUTEX.acquire();
                    clientsEsperant --;
                    PERRUQUER.release();
                    MUTEX.release();
                    System.out.println("Perruquer: preparat per tallar el monyo a un nou client.");
                    Thread.sleep(1500);
                    System.out.println("Perruquer: ja ho tens, t'ha quedat molt bé!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    static class Client extends Thread{

        private int id;
        
        public Client(int id){
            this.id = id;
        }
        
        @Override
        public void run() {
            try {
                MUTEX.acquire();
                if (clientsEsperant < cadires) {
                    clientsEsperant++;
                    CLIENTS.release();
                    MUTEX.release();
                    PERRUQUER.acquire();
                    System.out.println("");
                }
            } catch (Exception e) {
            }
        }
        
    }
}
