package solucioexamen;



/**************************************/
/* 		 Exercici 3 Examen PSP  	  */
/* 		 IES LLu�s Simarro            */
/*		 Curs 19/20                   */
/**************************************/


// imports

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.Semaphore;

public class Exercici3 extends RecursiveAction {

	// llindar execuci� recursiva / iterativa
	private static final int LLINDAR = 5;
	// per guardar la quantitat de n�meros parells i imparells i n�mero concret
	static int parells = 0;
	static int imparells = 0;
	static int numeros = 0;
	// atributs per al constructor
	private int[] arrayNombres;
	private int inici, fi;
	private int num;

	// Acc�s en exclusi� m�tua als acumuladors
	private static final Semaphore MUTEX_PARELLS = new Semaphore(1);
	private static final Semaphore MUTEX_IMPARELLS = new Semaphore(1);
	private static final Semaphore MUTEX_NUMERO = new Semaphore(1);

	// constructor
	public Exercici3(int[] arrayNombres, int num, int inici, int fi) {
		this.arrayNombres = arrayNombres;
		this.num = num;
		this.inici = inici;
		this.fi = fi;
	}

	@Override
	protected void compute() {
		if ((fi - inici) <= LLINDAR) {
			// cas trivial, sumem comprovem directament els valors del car�cter
			// en el fragment de cadena
			for (int i = inici; i < fi; i++) {
				// acumulem el valor en la variable est�tica quantitat
				// comprovem si s�n imparells o parells
				try {
					if ((arrayNombres[i] % 2) == 0) {
						// accedir en exclusi� m�tua
						MUTEX_PARELLS.acquire();
						// incrementar valor variable parells
						parells++;
						// alliberem el sem�for
						MUTEX_PARELLS.release();
					} else {
						// n�mero imparell
						// acc�s en exclusi� m�tua
						MUTEX_IMPARELLS.acquire();
						// incrementar valor variable imparells
						imparells++;
						// alliberem el sem�for
						MUTEX_IMPARELLS.release();
					}
					//per comprovar si coincideix amb el n�mero triat en cada execuci�
					if (arrayNombres[i] == num) {
						// accedir en exclusi� m�tua
						MUTEX_NUMERO.acquire();
						// incrementar valor variable num
						numeros++;
						// alliberem el sem�for
						MUTEX_NUMERO.release();
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// entrem en el cas recursiu
			// partim l'array per la meitat i cridem a les 2 parts
			int meitat = (inici + fi) / 2 + 1;
			// hem de crear una nova tasca per a la primera part:
			Exercici3 tasca1 = new Exercici3(arrayNombres, num, inici, meitat);
			// creem una altra per la segona meitat
			Exercici3 tasca2 = new Exercici3(arrayNombres, num,  meitat, fi);
			// fiquem les tasquen en la cua d'execuci�
			tasca1.fork();
			tasca2.fork();
			// i ara esperem a que acaben
			tasca1.join();
			tasca2.join();
		}
	}

	public static void main(String[] args) {
		int longitud =500000;
		//array de nombres
		int arrayNombres[]= new int[longitud];
		///Per obtenir els valors aleaoris
		Random rn = new Random();
		//inicialitzem l'array amb valors aleatoris
		for(int i =0;i<longitud;i++) {
			//valor aleatori entre 1 i 10
			arrayNombres[i]=rn.nextInt(10)+1;
		}

		// creem un nou gestor de fils forkjoin
		ForkJoinPool pool = new ForkJoinPool();
		// inicialitzem valors inici i fi
		int inici = 0;
		int fi = arrayNombres.length;
		// creem la tasca inicial per a tot l'array
		// valor aleatori a trobar:
		//valor aleatori entre 1 i 10
		int numero = rn.nextInt(10)+1;
		Exercici3 tasca = new Exercici3(arrayNombres, numero, inici, fi);
		// cridem a la tasca
		pool.invoke(tasca);
		// esperem que la tasca acabe
		tasca.join();
		// mostrem el resultat
		System.out.println("Quantitat de nombres parells: " + tasca.parells);
		System.out.println("Quantitat de nombres imparells: " + tasca.imparells);
		System.out.println("Quantitat de nombres "+ numero +": " + tasca.numeros);
		System.out.println("Quantitat total de nombres: " + (tasca.parells+ tasca.imparells));

	}

}
