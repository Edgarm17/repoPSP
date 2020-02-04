/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semafors;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Edgar
 */
public class Perruqueria {

    private static final Semaphore CLIENT = new Semaphore(0);
    private static final Semaphore MUTEX = new Semaphore(1);
    private static final Semaphore PERRUQUER_PREPARAT = new Semaphore(0);
    private static final Semaphore CLIENT_COBRAT = new Semaphore(0);
    private static final Semaphore PERRUQUER_ACABA = new Semaphore(0);
    private static int totalClients = 0;
    private static int clientsEsperant = 0;
    
    public static void main(String[] args) throws InterruptedException {
        Perruquer p = new Perruquer();
        
        p.start();
        for (int i = 1; i <= 15; i++) {
            new Client(i).start();
            
        }
        
        
        
    }
    
    static class Perruquer extends Thread{

        @Override
        public void run() {
            System.out.println("Perruquer dormint...");
            while (totalClients<15) {
                try {
                    CLIENT.acquire();
                    System.out.println("Perruquer: preparat per tallar el monyo a un nou client.");
                    PERRUQUER_PREPARAT.release();
                    clientsEsperant--;
                    sleep(1500);
                    System.out.println("Perruquer: ja ho tens, t'ha quedat molt bé!");
                    PERRUQUER_ACABA.release();
                    CLIENT_COBRAT.acquire();
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
            }
            System.out.println("Perruquer dormint...");
            
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
                if (clientsEsperant<5) {
                    totalClients++;
                    clientsEsperant++;
                    sleep(800);
                    System.out.println("Client "+id+" ha arribat.");
                    MUTEX.release();
                    CLIENT.release();
                    PERRUQUER_PREPARAT.acquire();
                    System.out.println("Client "+id+": tallant-se el monyo. Clients en espera: "+clientsEsperant);
                    
                    PERRUQUER_ACABA.acquire();
                    System.out.println("Client "+id+" paga al perruquer i se´n va.");

                    CLIENT_COBRAT.release();
                    
                      
                
            }else{
                totalClients++;
                System.out.println("Client "+id+" se'n va");
                MUTEX.release();
            }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            
            
        }
    }
    
}
