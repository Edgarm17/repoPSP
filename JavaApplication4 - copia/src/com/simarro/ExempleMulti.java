/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simarro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Edgar
 */
public class ExempleMulti {
    static class Multiplicacio implements Callable<Integer> {

        private int op1;
        private int op2;

        public Multiplicacio(int op1, int op2) {
            this.op1 = op1;
            this.op2 = op2;
        }

        @Override
        public Integer call() throws Exception {
            return op1 * op2;
        }

    }

//    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
//
//        List<Multiplicacio> llistaTasques = new ArrayList<Multiplicacio>();
//
//        for (int i = 0; i < 10; i++) {
//            Multiplicacio calcula = new Multiplicacio((int) (Math.random() * 10), (int) (Math.random() * 10));
//            llistaTasques.add(calcula);
//        }
//        
//        List<Future<Integer>> llistaResultats;
//        
//        llistaResultats = executor.invokeAll(llistaTasques);
//        
//        executor.shutdown();
//        
//        for (int i = 0; i < llistaResultats.size(); i++) {
//            Future<Integer> resultat = llistaResultats.get(i);
//            try {
//                System.out.println("Resultat tasca "+i+" és: " + resultat.get());
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            }
//        }
//    }
}
