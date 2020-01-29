/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici1;

/**
 *
 * @author Edgar
 */
public class ActivitatGestioFilsEdgar extends Thread{
    
    private String id;

    public ActivitatGestioFilsEdgar(String id) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread primer = new ActivitatGestioFilsEdgar("Pepe");
	Thread segon = new ActivitatGestioFilsEdgar("Jose");
        
        primer.start();
        segon.start();
        
        try {
            primer.join();
            segon.join();
        } catch (Exception e) {
            System.out.println("Error al fil");
        }
        
        System.out.println("Final del fil principal!");
    }
    
}
