package activitatproductorconsumidoredgar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar
 */
public class ActivitatProductorConsumidorEdgar {

    private static Deque<Integer> cua = new ArrayDeque<>(10);
    private static int produits = 0;
    private static int consumits = 0;
    private static Semaphore MUTEX = new Semaphore(1);
    private static Semaphore HI_HA_ELEMENTS = new Semaphore(0);
    private static Semaphore NO_QUEDEN_ELEMENTS = new Semaphore(1);

    public static void main(String[] args) {

        for (int i = 1; i < 5; i++) {
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
            while (produits <= 20) {
                try {
                    if (cua.size() <= 0 && cua.size() <= 9) {
                        NO_QUEDEN_ELEMENTS.acquire();
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
                        HI_HA_ELEMENTS.release();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ActivitatProductorConsumidorEdgar.class.getName()).log(Level.SEVERE, null, ex);
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
            boolean elementConsumit = false;
            while (!elementConsumit) {
                try {
                    HI_HA_ELEMENTS.acquire();
                    MUTEX.acquire();
                    if (cua.size()>0) {
                        int num = cua.pollFirst();
                        consumits++;
                        Iterator<Integer> it = cua.iterator();
                        System.out.print("Hem consumit (C" + id + "): ");
                        while (it.hasNext()) {
                            System.out.print(it.next() + " ");

                        }
                        System.out.println(":: Consumits: " + consumits);
                        
                        MUTEX.release();
                        HI_HA_ELEMENTS.release();
                        elementConsumit = true;
                    } else {
                        MUTEX.release();
                        NO_QUEDEN_ELEMENTS.release();
                    }
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(ActivitatProductorConsumidorEdgar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            MUTEX.release();

        }

    }

}
