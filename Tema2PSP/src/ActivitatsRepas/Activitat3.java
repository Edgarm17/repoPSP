/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActivitatsRepas;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Edgar
 */
public class Activitat3 {

    private static String cadenes[] = {"La Llosa de Ranes", "Xàtiva", "Canals", "Alcúdia", "Genovés", "Sorió", "Vallés", "Llanera de Ranes", "Rotglà i Corberà", "Novetlè"};
    private static int LLINDAR = 2;
    private static String MINIMA = "La Llosa de Ranes";

    public static class Fil extends RecursiveAction {

        private int inici;
        private int fi;
        private String[] array;

        public Fil(int inici, int fi, String[] array) {
            this.inici = inici;
            this.fi = fi;
            this.array = array;
        }

        @Override
        protected void compute() {
            int aux = fi - inici;
            if (aux <= LLINDAR) {

                getMinimaSeq();

            } else {
                getMinimaRec();
            }
            
            
        }

        public void getMinimaSeq() {
            for (int i = inici; i < fi; i++) {
                if (array[i].length() < MINIMA.length()) {
                        MINIMA = array[i];
                }

            }

        }

        public void getMinimaRec() {
            int mig = (inici + fi) / 2 + 1;
            Fil f1 = new Fil(inici, mig, array);

            Fil f2 = new Fil(mig, fi, array);
            invokeAll(f1, f2); 
        }

        public static void main(String[] args) {
            int inici = 0;
            int fi = cadenes.length;
            Fil s = new Fil(inici, fi, cadenes);
            ForkJoinPool fjp = new ForkJoinPool();

            fjp.invoke(s);
            
            s.join();
            System.out.println(MINIMA);
            System.out.println("Final fi principal");
        }

    }

}
