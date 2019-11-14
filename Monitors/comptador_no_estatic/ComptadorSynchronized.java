package comptadorsynchronized;

public class ComptadorSynchronized {
	// Classe on està el programa principal per incrementar
	// una variable en exclusió mútua
	public static void main(String[] args) {
		// Nou objecte comptador, que serà el mateix per a tots els processos
		Comptador comptador = new Comptador();
		// Creem dos fils, amb id diferent, però el mateix comptador
		Thread Fil1 = new ProcesComptador(1, comptador);
		Thread Fil2 = new ProcesComptador(2, comptador);
		// Iniciem els fils
		Fil1.start();
		Fil2.start();
	}

}

