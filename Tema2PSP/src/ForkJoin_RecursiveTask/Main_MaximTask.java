/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ForkJoin_RecursiveTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Edgar
 */
public class Main_MaximTask {

    static class Tasca extends RecursiveTask<Short> {

        private static final int LLINDAR = 10000000;
        private short[] array;
        private int inici, fi;

        public Tasca(short[] array, int inici, int fi) {
            this.array = array;
            this.inici = inici;
            this.fi = fi;
        }

        private short getMaxSequencial() {
            short max = array[inici];
            for (int i = inici + 1; i < fi; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            return max;
        }

        private short getMaxRecursiu() {
            Tasca t1;
            Tasca t2;
            int mig = (inici + fi) / 2 + 1;
            t1 = new Tasca(array, inici, mig);
            t1.fork();
            t2 = new Tasca(array, mig, fi);
            t2.fork();
            return (short) Math.max(t1.join(), t2.join());
        }

        @Override
        protected Short compute() {
            if (fi - inici <= LLINDAR) {
                return getMaxSequencial();
            } else {
                return getMaxRecursiu();
            }
        }

        public static void main(String[] args) {
            short[] dades = createArray(100000000);
            System.out.println("Inici càlcul");
            ForkJoinPool pool = new ForkJoinPool();
            int inici = 0;
            int fi = dades.length;
            Tasca tasca = new Tasca(dades, inici, fi);

            long time = System.currentTimeMillis();
            // crida la tasca i espera que es completen
            int result1 = (int) pool.invoke(tasca);
            // màxim
            int result = tasca.join();
            System.out.println("Temps utilitzat:"
                    + (System.currentTimeMillis() - time));
            System.out.println("Màxim es " + result);
        }

        private static short[] createArray(int size) {
            short[] ret = new short[size];
            for (int i = 0; i < size; i++) {
                ret[i] = (short) (1000 * Math.random());
                if (i == ((short) (size * 0.9))) {
                    ret[i] = 1005;
                }
            }
            return ret;
        }

    }
}
