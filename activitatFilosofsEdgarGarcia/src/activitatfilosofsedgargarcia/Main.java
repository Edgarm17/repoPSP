package activitatfilosofsedgargarcia;

import java.util.concurrent.Semaphore;

/**
 * Clase principal (Main) que lanza la aplicación Filósofos.
 *
 * @author Odei
 * @version 28.12.2015
 */
public class Main {
    /**
     * Variable entera que establece la cantidad de filósofos a utilizar.
     */
    protected final static int numFilosofos = 5;
    
    /**
     * Array bidimensional de enteros con tantas filas como filósofos. 
     * Cada fila está compuesta por un array de enteros que representan
     * los palillos que puede utilizar cada filósofo.
     */
    protected final static int[][] palFilosofo = {
        {0, 4},                                                                 // Filósofo 1
        {1, 0},                                                                 // Filósofo 2
        {2, 1},                                                                 // Filósofo 3
        {3, 2},                                                                 // Filósofo 4
        {4, 3}                                                                  // Filósofo 5
    };

    /**
     * Array de Semaphores que simbolizan los palillos de los filósofos.
     * Existen tantos palillos como filósofos declarados.
     */
    protected final static Semaphore[] palSemaforo = new Semaphore[numFilosofos];

    /**
     * Método estático que define e inicializa los variables utilizadas 
     * para construir los hilos de la clase Filosofo. 
     * Creando un número concreto de filósofos e iniciándolos.
     */
    protected static void iniciarHilos() {
        for (int i = 0; i < numFilosofos; i++) {                                // Creamos tantos Semaphores como número de filosofos declarados, agregándoles 1 permiso a cada uno
            palSemaforo[i] = new Semaphore(1);                                  // ya que cada palillo/Semaphore sólo puede estar siendo usado por un único filósofo al mismo tiempo.
        }
        for (int i = 0; i < numFilosofos; i++) {                                // Creamos los objetos de tipo Filosofo enviándole al constructor la variable que itera el bucle
            new Filosofo(i, palSemaforo, palFilosofo).start();                  // (a modo de id), el array de Semaphores y el array de palillos, y posteriormente los iniciamos.
            Interfaz.setEstado(i, "pensando");                                  // Además les asignamos las imágenes iniciales a las etiquetas de la interfaz.
            Interfaz.setMano(i, "empty");
        }
    }
    
    /**
     * Genera una Interfaz que controla y visualiza la ejecución del programa.
     *
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
    }
}