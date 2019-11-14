package exemplesynchronized;

public class ExempleSynchronized {
	// variable compartida
	private static int comptador = 0;

	static class Sumador extends Thread {

		private int id;

		//mètode per incrementar el valor de la variable estàtica
		//no sincronitzat
		public static void incrementa() {			
				comptador++;		
		}

		public Sumador(int id) {
			this.id = id;
		}
		//tasca que realitza en procés/fil
		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				incrementa();
			}
			System.out.println("Valor final sumador  " + this.id + ": " + comptador);
		}
	}

	public static void main(String[] args) {
		//Valor inicial del comptador
		System.out.println("Valor inicial: " + comptador);
		// Creem i fiquem en marxa dos processos amb id diferent
		// Els dos accediran al comptador, que és una variable compartida (static)
		new Sumador(1).start();
		new Sumador(2).start();
	}
}
