package monitorsimple;

/**
 * Exemple de Monitor senzill. Encapsula una variable protegida per l'abstracció
 * i posseeix una interfície de dos mètodes per incrementar-la i minvar-la i un
 * tercer per conéixer el valor del recurs protegit.
 */

class Monitor {
	private static int Dada; // recurs protegit
	// variables per a les condicions
	// Es poden fer 10 operacions suma o resta seguides
	// Una vegada fetes es permet fer l'operació contraria
	// inicialment podem fer 10 restes
	private int sumes = 10;
	private int restes = 0;

	public Monitor(int DadaInicial) {
		this.Dada = DadaInicial;
	}

	// métode per incrementar el valor de la Dada (en exclusió mútua)
	public synchronized void SUMA() {
		// Si el valor de restes és 0, esperem
		while (restes == 0) {
			try {
				// Indiquem que anem a esperar
				System.out.println("Fil Sumador bloquejat");
				// Ens fiquem en la cua fins que ens avisen amb un notify / notifyAll 
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Modifiquem el valor de la dada (recurs protegit en exclusió mútua (synchronized)
		//En aquest cas, l'incrementem
		Dada++;
		//Gestionem els comptadors de restes i sumes
		//per arribar a la condició de bloqueig / desbloqueig
		restes--;
		sumes++;
		System.out.println("Sumador sumes: "+sumes+ ", Sumador Dada: " + Dada);
		//si hem realitzat la quantitat consecutiva màxima permesa de sumes (10), ara sumes = 10
		//simultàniament el valor de restes haurà minvat fins a ser 0
		//Avisem als que estiguen en la cua que ja poden continuar.
		if (sumes == 10) {
			notifyAll();
		}
	}

	public synchronized void RESTA() {
		// si el valor de sumes és 0 esperem
		while (sumes == 0) {
			try {
				// Indiquem que anem a esperar
				System.out.println("Fil Restador bloquejat");
				// Ens fiquem en la cua fins que ens avisen amb un notify / notifyAll 
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Modifiquem el valor de la dada (recurs protegit en exclusió mútua (synchronized)
		//En aquest cas, el minvem
		Dada--;
		//Gestionem els comptadors de restes i sumes
		//per arribar a la condició de bloqueig / desbloqueig
		sumes--;
		restes++;
		System.out.println("Restador restes: "+restes+", Restador Dada: " + Dada);
		//si hem realitzat la quantitat consecutiva màxima permesa de restes (10), ara restes = 10
		//simultàniament el valor de restes haurà minvat fins a ser 0
		//Avisem als que estiguen en la cua que ja poden continuar.
		if (restes == 10) {
			notifyAll();
		}
	}

	public synchronized String valorDada() {
		return (new Integer(this.Dada).toString());
	}
} // fi del monitor 



//Procés que suma a la dada protegida del monitor
class FilSumador extends Thread {
	private Monitor monitorDada;

	public FilSumador(Monitor m) {
		this.monitorDada = m;
	}
	// Ha de sumar 100 vegades al valor de la dada protegida del monitor
	public void run() {
		for (int i = 0; i < 100; i++) {
			monitorDada.SUMA();
		}
		System.out.println("Valor final dada Sumador: "+monitorDada.valorDada());
		
	}
}

//Procés que resta a la dada protegida del monitor
class FilRestador extends Thread {
	private Monitor monitorDada;

	public FilRestador(Monitor m) {
		this.monitorDada = m;
	}

	// Ha de restar 100 vegades al valor de la dada protegida del monitor
	public void run() {
		for (int i = 0; i < 100; i++) {
			monitorDada.RESTA();			
		}
		System.out.println("Valor final dada Restador: "+monitorDada.valorDada());
	}
}

public class MonitorSimple {
	public static void main(String[] args) {
		//Iniciem el monitor amb un valor inicial del recurs
		Monitor m = new Monitor(1000);
		//Iniciem els procesos FilSumador i FilRestador
		//Els passem el mateix monitor als dos
		new FilSumador(m).start();
		new FilRestador(m).start();
	}
}

