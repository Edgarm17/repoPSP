/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici3;

import java.util.ArrayList;

/**
 *
 * @author Edgar
 */
public class Recollidor {
    
    int resultat;
    
    private ArrayList<Integer> resultats = new ArrayList<>();

    public Recollidor() {
    }
    
//    public synchronized void anyadirValor(int valor){
//        resultats.add(valor);
//    }
    
    public synchronized void mostrarSuma(int valor){
        resultats.add(valor);
        resultat = 0;
        for (int i = 0; i < resultats.size(); i++) {
            resultat+=resultats.get(i);
        }
        System.out.println("El resultat de la suma de valors es "+resultat);
    }
    
}
