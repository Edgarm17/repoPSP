package ScheduledExecutor;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ActivitatFarmacia implements Runnable {

    public static ArrayList<String> missatges = new ArrayList<>();

    public ActivitatFarmacia(ArrayList<String> missatges) {
        this.missatges = missatges;
    }

    public void run() {
        int random;
        random = (int) (Math.random() * 5);
        System.out.println(missatges.get(random).toString());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        missatges.add("Menja 5 peces de fruita al dia");
        missatges.add("Fes esport diariament");
        missatges.add("Beu dos litres daigua al dia");
        missatges.add("Camina 5km cada dia");
        missatges.add("Hidratat be");
        ActivitatFarmacia fil = new ActivitatFarmacia(missatges);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(fil, 0, 2, TimeUnit.SECONDS);
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Error");
        }
        executor.shutdown();
        System.out.println("Completat");

    }

}
