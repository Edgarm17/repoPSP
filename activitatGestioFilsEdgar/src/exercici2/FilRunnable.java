/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici2;

/**
 *
 * @author Edgar
 */
public class FilRunnable implements Runnable{
    
    private String id;

    public FilRunnable(String id) {
        this.id = id;
    }
    
    

    @Override
    public void run() {
       try {
            for (int i = 0; i < 5; i++) {
                System.out.println(id+" : "+i);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Error al fil");
        }
    }
    
    public static void main(String[] args) {
        FilRunnable filRunnable1 = new FilRunnable("Pepe");
        FilRunnable filRunnable2 = new FilRunnable("Jose");
        
        Thread primer = new Thread(filRunnable1);
        Thread segon = new Thread(filRunnable2);
        
        primer.start();
        segon.start();
        
        try {
            primer.join();
            segon.join();
        } catch (Exception e) {
            System.out.println("Error en el fil!");
        }
        
        System.out.println("Fi del programa principal!");
        
    }
    
    
    
}
