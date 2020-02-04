/*
 * Trobar tots els números primers més xicotets de 5000
 * Crear una tasca cada 1000 nums (es a dir, 5)
 * Cada tasca mostra per pantalla els numeros prims dins del seu interval
 */
package ThreadPoolExecutor_Runnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Edgar
 */
public class Main {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            
            executor.execute(new ExecutaFil(i* 1000));
        }
        
        executor.shutdown();

    }

    static class ExecutaFil implements Runnable {

        public int num;

        public ExecutaFil(int num) {
            this.num = num;
        }

        @Override
        public void run() {

            for (int i = num-1000; i <= num; i++) {
                if (isPrimeNumber(i)) {
                    System.out.println("Tasca del "+(num-1000)+" al "+num+" : "+i);
                }
            }

        }

        public static boolean isPrimeNumber(int i) {
            int factors = 0;
            int j = 1;

            while (j <= i) {
                if (i % j == 0) {
                    factors++;
                }
                j++;
            }
            return (factors == 2);
        }

    }

}
