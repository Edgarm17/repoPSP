/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asdf;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Edgar
 */
public class Asdf {

    public static void main(final String... args) throws
            InterruptedException, ExecutionException {
        //mostrem hora actual abans d'execució
        Calendar calendari = new GregorianCalendar();
        System.out.println("Inici: "
                + calendari.get(Calendar.HOUR_OF_DAY)
                + ":" + calendari.get(Calendar.MINUTE) + ":"
                + calendari.get(Calendar.SECOND));
        // Crea un pool de 2 fils
        final ScheduledExecutorService schExService = Executors.newScheduledThreadPool(1);
        // Crea objecte Runnable
        final Runnable ob = new Asdf().new ExecutaFil();
        // Programa Fil, s'inicia als 2 segons i després es va executant cada 3 segons 
        schExService.scheduleAtFixedRate(ob, 2, 3,TimeUnit.SECONDS);
        // Espera per acabar 10 segons
        schExService.awaitTermination(13, TimeUnit.SECONDS);
        // shutdown
        schExService.shutdownNow();
        System.out.println("Completat");
    }
    // Fil Runnable

    class ExecutaFil implements Runnable {

        @Override
        public void run() {
            Calendar calendari = new GregorianCalendar();
            System.out.println("Hora execució tasca: "
                    + calendari.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendari.get(Calendar.MINUTE) + ":"
                    + calendari.get(Calendar.SECOND));
            System.out.println("Tasca en execució");
            System.out.println("Execució acabada");
        }
    }


}
