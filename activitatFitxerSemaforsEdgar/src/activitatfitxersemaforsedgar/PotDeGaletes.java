package activitatfitxersemaforsedgar;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PotDeGaletes {

	// inicialitzem el pot de GALETES
	private static int galetes = 10; // el pot pot tindre 10 galetes
	// comptador de pots
	private static int pot = 1; // el primer pot ja est� ficat
	// inicialitzem els sem�fors per avisar que hi ha galetes i que no n'hi ha
	private static final Semaphore NO_HI_HA_GALETES = new Semaphore(0);
	// inicilitzem el sem�for de galetes disponibles a 1 perqu� el primer xiquet
	// que arribe puga agafar-ne
	private static final Semaphore HI_HA_GALETES = new Semaphore(1);
	// Sem�for per accedir en exclusi� m�tua al recurs compartit galetes
	private static final Semaphore MUTEX = new Semaphore(1);

	static class Xiquet extends Thread {

		private int id;

		public Xiquet(int id) {
			this.id = id;
		}

		public void run() {
			boolean galeta_menjada;
			for (int i = 0; i < 20; i++) { // vol menjar 20 galetes
				galeta_menjada = false;
				try {
					while (!galeta_menjada) {
						HI_HA_GALETES.acquire(); // espera a accedir al pot
						MUTEX.acquire(); // accedeix al pot en exclusi� m�tua
						if (galetes == 0) {
							System.out.println("Xiquet (" + id + "): S'han acabat les galetes Mare!!!");
							MUTEX.release();
							NO_HI_HA_GALETES.release(); // avisa a la mare que no hi ha galetes
						} else {
							galetes--; // menja una galeta
							MUTEX.release();
							System.out.println("Galeta " + galetes + " menjada pel xiquet " + id + "(" + i + ")");
							HI_HA_GALETES.release(); // allibera el pot de galetes perqu� altres xiquets puguen menjar
							galeta_menjada = true;
						}
					}

				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				// Deixem un temps aleatori entre galeta i galeta
				try {
					Random rntemps = new Random();
					int temps = rntemps.nextInt(1000);
					Thread.sleep(temps);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	static class Mare extends Thread {
		public void run() {
			while (true) { // sempre reposar� les galetes
				try {
					NO_HI_HA_GALETES.acquire(); // espera que un xiquet l'avise
												// que no hi ha m�s galetes
					MUTEX.acquire(); // accedeix al pot en exclusi� m�tua
					galetes = 10; // torna a plenar el pot de les galetes
					pot++;
					MUTEX.release();
					System.out.println("Ja en tens unes quantes m�s: pot n�mero: " + pot);
					HI_HA_GALETES.release(); // avisa al xiquet que ja est� el
												// pot disponible
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Mare().start();
		for (int i = 1; i < 5; i++) {
			new Xiquet(i).start();
		}

	}

}
