package activitatproductorconsumidoredgar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Edgar
 */
public class ActivitatProductorConsumidorEdgar {

    private static Deque<Integer> cua = new ArrayDeque<>(10);
    private static int produits = 0;
    private static int consumits = 0;
    private static Semaphore MUTEX = new Semaphore(1);
    private static Semaphore PLENS = new Semaphore(0);
    private static Semaphore BUITS= new Semaphore(10);

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Productor(i).start();
            new Consumidor(i).start();
        }

    }

    public static class Productor extends Thread {

        private int id;

        public Productor(int id) {
            this.id = id;
        }

        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    	BUITS.acquire();
                        MUTEX.acquire();
                        
                        int num = (int) (Math.random() * 100);
                        cua.add(num);
                        produits++;
                        Iterator<Integer> it = cua.iterator();
                        System.out.print("Hem produit (P" + id + "): ");
                        while (it.hasNext()) {
                            System.out.print(it.next() + " ");

                        }
                        System.out.println(":: Produïts: " + produits);

                        Thread.sleep((long) (Math.random() * 900));
                        MUTEX.release();
                        PLENS.release();
                        
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
                
        }
    }

    public static class Consumidor extends Thread {

        private int id;

        public Consumidor(int id) {
            this.id = id;
        }

        public void run() {
           
            for (int i = 0; i < 20; i++) {
                try {
                    
                    PLENS.acquire();
                    MUTEX.acquire();
                    
                        int extraido = cua.pollFirst();
                        consumits++;
                        Iterator<Integer> it = cua.iterator();
                        System.out.print("Hem consumit (C" + id + "): ");
                        while (it.hasNext()) {
                            System.out.print(it.next() + " ");

                        }
                        System.out.println(":: Consumits: " + consumits);
                        
                        MUTEX.release();
                        BUITS.release();
                        
                   
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            

        }

    }

}
