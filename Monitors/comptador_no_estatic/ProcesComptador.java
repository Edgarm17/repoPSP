package comptadorsynchronized;

public class ProcesComptador extends Thread {
	// Classe del procés que incrementarà el comptador
	// Té un objecte comptador intern, que podrà ser el mateix que el d'altres
	// processos
	protected Comptador comptador = null;
	// Per identificar el procés
	private int id;

	// Constructor
	public ProcesComptador(int id, Comptador comptador) {
		this.comptador = comptador;
		this.id = id;
	}

	// tasca que realitza el procés/fil quan es fica en marxa
	@Override
	public void run() {
		// incrementa en 100000 el valor del comptador
		for (int i = 0; i < 100000; i++) {
			comptador.incrementa();
		}
		// mostra el valor final del comptador
		System.out.println("Valor final ProcesComptador  " + this.id + ": " + comptador.mostra());
	}
}
