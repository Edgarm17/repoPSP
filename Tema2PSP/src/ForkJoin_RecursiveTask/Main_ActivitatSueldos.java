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
 * @author sergi
 */
public class Main_ActivitatSueldos {

    public static int LLINDAR = 70000;

    public static class MaxSou extends RecursiveTask<Short> {

        private int inici;
        private int fi;
        private short[] array;

        public MaxSou(int inici, int fi, short[] array) {
            this.inici = inici;
            this.fi = fi;
            this.array = array;
        }

        public short getMaxSeq() {
            short max = array[inici];
            for (int i = inici; i < fi; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            return max;
        }

        public short getMaxReq() {
            int mig = (inici + fi) / 2 + 1;
            MaxSou m1 = new MaxSou(inici, mig, array);
            m1.fork();
            MaxSou m2 = new MaxSou(mig, fi, array);
            m2.fork();
            return (short) Math.max(m1.join(), m2.join());
        }

        @Override
        protected Short compute() {
            if (fi - inici <= LLINDAR) {
                return getMaxSeq();
            }
            return getMaxReq();
        }

    }

    private static short[] createArray(int size) {
        short[] ret = new short[size];
        for (int i = 0; i < size; i++) {
            ret[i] = (short) (50000 * Math.random());
        }
        return ret;
    }

    public static void main(String[] args) {
        short[] arr = createArray(200000);
        System.out.println("Inici Programa");

        ForkJoinPool fjp = new ForkJoinPool();
        int inici = 0;
        int fi = arr.length;

        MaxSou m = new MaxSou(inici, fi, arr);

        int r = fjp.invoke(m);
        int result = m.join();
        System.out.println("Resultat Final: " + result);

    }

}
