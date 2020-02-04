/*
 * Trobar tots els numeros divisibles per 2 i per 3 menors que 100
 * Crear tasca per cada numero (100 tasques en este cas)
 * Mostrar sols els numeros múltiples de 2.
 */
package ThreadPoolExecutor_Callable;

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
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        List<Tasca> llistaTasques = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Tasca t = new Tasca(i);
            llistaTasques.add(t);
        }

        List<Future<Boolean>> llistaResultats;

        llistaResultats = executor.invokeAll(llistaTasques);

        executor.shutdown();

        for (int i = 0; i < llistaResultats.size(); i++) {
            Future<Boolean> resultat = llistaResultats.get(i);
            if (i % 2 == 0) {
                try {
                    System.out.println("Resultat num " + i + " és: " + resultat.get());
                } catch (Exception e) {
                }
            }

        }
    }

    static class Tasca implements Callable<Boolean> {

        int num;

        public Tasca(int num) {
            this.num = num;
        }

        @Override
        public Boolean call() throws Exception {

            return num % 2 == 0 && num % 3 == 0;
        }

    }
}
