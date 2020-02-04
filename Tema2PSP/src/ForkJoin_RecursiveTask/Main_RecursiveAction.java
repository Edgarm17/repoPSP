/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ForkJoin_RecursiveTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author sergi
 */
public class Main_RecursiveAction {

    private static int SUMA = 0;
    private static int LLINDAR = 300;

    public static class Sumar extends RecursiveAction {

        private int inici;
        private int fi;
        private int[] array;

        public Sumar(int inici, int fi, int[] array) {
            this.inici = inici;
            this.fi = fi;
            this.array = array;
        }

        @Override
        protected void compute() {
            if (fi - inici <= LLINDAR) {
                sumaSeq();
            } else {
                sumaReq();
            }
        }

        private void sumaSeq() {
            for (int i = inici; i < fi; i++) {
                SUMA += array[i];
            }
        }

        private void sumaReq() {
            int mig = (inici + fi) / 2 + 1;
            Sumar s1 = new Sumar(inici, mig, array);
            Sumar s2 = new Sumar(mig, fi, array);
            invokeAll(s1, s2);
        }

    }

    private static int[] createArray(int size) {
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = i;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] array = createArray(1000);
        int inici = 0;
        int fi = array.length;
        Sumar s = new Sumar(inici, fi, array);
        ForkJoinPool fjp = new ForkJoinPool();

        fjp.invoke(s);
        System.out.println(SUMA);
        s.join();

        System.out.println("Final fi principal");
    }
}
